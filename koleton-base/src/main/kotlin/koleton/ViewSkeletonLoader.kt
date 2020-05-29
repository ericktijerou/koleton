package koleton

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.ShimmerFrameLayout
import koleton.annotation.ExperimentalKoletonApi
import koleton.base.R
import koleton.layout.KoletonLayout
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
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Error", throwable.message.orEmpty())
    }

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
                    val skeletonView = generateSkeletonView(skeleton, view)
                    targetDelegate.success(skeletonView)
                }
            }
            val skeletonDelegate = delegateService.createSkeletonDelegate(skeleton, targetDelegate, lifecycle, mainDispatcher, deferred)
            deferred.invokeOnCompletion { throwable ->
                loaderScope.launch(Dispatchers.Main.immediate) { skeletonDelegate?.onComplete() }
            }
            deferred.await()
        }

    override fun hide(target: Target?) {
        (target as? ViewTarget)?.apply {
            val koletonLayout = view.getParentViewGroup() as KoletonLayout
            view.lparams(koletonLayout)
            val originalParent = koletonLayout.getParentViewGroup()
            koletonLayout.removeView(view)
            originalParent.removeView(koletonLayout)
            originalParent.addView(view)
            view.visible()
        }
    }

    private fun generateSkeletonView(skeleton: Skeleton, view: View): View {
        val parent = view.getParentViewGroup()
        val koletonLayout = KoletonLayout(view)
        koletonLayout.layoutParams = view.layoutParams
        koletonLayout.id = view.id
        parent.removeView(view)
        koletonLayout.addView(view)
        parent.addView(koletonLayout)
        koletonLayout.showSkeleton()
        return view
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