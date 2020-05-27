package koleton

import android.content.Context
import android.graphics.drawable.PaintDrawable
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.ericktijerou.mockplaceholder.RoundedBackgroundColorSpan
import com.facebook.shimmer.ShimmerFrameLayout
import koleton.annotation.ExperimentalKoletonApi
import koleton.base.R
import koleton.memory.DelegateService
import koleton.memory.SkeletonService
import koleton.skeleton.Skeleton
import koleton.target.Target
import koleton.target.ViewTarget
import koleton.util.*
import kotlinx.coroutines.*

@OptIn(ExperimentalKoletonApi::class)
internal class ViewSkeletonLoader(
    private val context: Context,
    override val defaults: DefaultSkeletonOptions
) : SkeletonLoader {

    companion object {
        private const val TAG = "MainSkeletonLoader"
    }

    private val loaderScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> }

    private val delegateService = DelegateService(this)

    override fun execute(skeleton: Skeleton) {
        val job = loaderScope.launch(exceptionHandler) { executeInternal(skeleton) }
        val target = skeleton.target as? ViewTarget
        target?.view?.skeletonManager?.setCurrentSkeletonJob(job)
    }

    private suspend fun executeInternal(skeleton: Skeleton) =
        withContext(Dispatchers.Main.immediate) {
            val (lifecycle, mainDispatcher) = SkeletonService().lifecycleInfo(skeleton)
            val targetDelegate = delegateService.createTargetDelegate(skeleton)
            val deferred = async(mainDispatcher, CoroutineStart.LAZY) {
                val target = skeleton.target as? ViewTarget
                target?.apply {
                    lifecycle.addObserver(target)
                    if (view.measuredWidth > 0 && view.measuredHeight > 0) {
                        val skeletonView = generateSkeletonView(skeleton, view)
                        targetDelegate.success(skeletonView)
                    }
                }
            }
            val skeletonDelegate = delegateService.createSkeletonDelegate(skeleton, targetDelegate, lifecycle, mainDispatcher, deferred)
            deferred.invokeOnCompletion { throwable ->
                loaderScope.launch(Dispatchers.Main.immediate) { skeletonDelegate?.onComplete() }
            }
            deferred.await()
        }

    override fun hide(target: Target?, skeletonId: Int) {
        (target as? ViewTarget)?.apply {
            val parentView = view.getParentViewGroup()
            val skeletonView = parentView.findViewById<View>(skeletonId)
            parentView.removeView(skeletonView)
        }
    }

    private fun generateSkeletonView(skeleton: Skeleton, view: View): View {
        val skeletonView = generateSkeletonByType(skeleton, view)
        return skeletonView.validateShimmer(skeleton.isShimmerEnabled, view.getParentViewGroup()) {
            val generatedId = ViewCompat.generateViewId()
            view.skeletonManager.setSkeletonId(generatedId)
            it.id = generatedId
            it.lparams(view)
        }
    }

    private fun generateSkeletonByType(skeleton: Skeleton, view: View): View {
        val skeletonView = when (view) {
            is ViewGroup -> generateViewGroupSkeleton(skeleton, view)
            is TextView -> generateTextViewSkeleton(view, R.color.colorGray, 6f)
            else -> generateSimpleSkeleton(R.color.colorGray, 6f)
        }
        return skeletonView.apply { id = view.id }
    }

    private fun generateViewGroupSkeleton(skeleton: Skeleton, viewGroup: ViewGroup): View {
        val skeletonViewGroup = viewGroup.cloneLayout()
        for (indexOfChild in 0 until viewGroup.childCount) {
            val childView = viewGroup.getChildAt(indexOfChild)
            val skeletonView = generateSkeletonByType(skeleton, childView)
            skeletonViewGroup.addView(skeletonView.lparams(childView))
        }
        return skeletonViewGroup
    }

    private fun generateSimpleSkeleton(color: Int, radius: Float): View {
        val skeletonView = View(context)
        skeletonView.background =
            PaintDrawable(context.getColorCompat(color)).apply { setCornerRadius(radius) }
        return skeletonView
    }

    private fun generateTextViewSkeleton(textView: TextView, color: Int, radius: Float): TextView {
        return TextView(context).apply {
            val colorInt = context.getColorCompat(color)
            setTextColor(context.getColorCompat(R.color.colorTransparent))
            text = spannable { background(colorInt, radius, textView.text) }
        }
    }

    private inline fun <T : View> T.validateShimmer(isShimmerEnabled: Boolean, parentView: ViewGroup? = null, block: (it: View) -> Unit): View {
        val newView = if (isShimmerEnabled) {
            generateShimmerLayout(parentView).also { it.addView(this) }
        } else {
            this
        }
        block(newView)
        return newView
    }

    private fun generateShimmerLayout(parentView: ViewGroup? = null): ShimmerFrameLayout {
        val shimmerLayout = context.inflate(R.layout.shimmer_layout, parentView) as ShimmerFrameLayout
        return shimmerLayout.apply {
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    startShimmer()
                }

                override fun onViewDetachedFromWindow(v: View) {
                    stopShimmer()
                }
            })
        }
    }
}