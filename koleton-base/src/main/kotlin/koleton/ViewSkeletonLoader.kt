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
import koleton.skeleton.Skeleton
import koleton.skeleton.TextViewSkeleton
import koleton.skeleton.ViewSkeleton
import koleton.target.Target
import koleton.target.ViewTarget
import koleton.util.*
import koleton.util.getColorCompat

@OptIn(ExperimentalKoletonApi::class)
internal class ViewSkeletonLoader(
    private val context: Context,
    override val defaults: DefaultSkeletonOptions
) : SkeletonLoader {

    companion object {
        private const val TAG = "MainSkeletonLoader"
    }

    private val delegateService = DelegateService(this)

    override fun execute(skeleton: Skeleton) {
        delegateService.createSkeletonDelegate(skeleton)
        val targetDelegate = delegateService.createTargetDelegate(skeleton)
        val target = skeleton.target as? ViewTarget
        target?.apply {
            view.afterMeasured {
                val skeletonView = generateSkeletonView(skeleton, it)
                targetDelegate.success(skeletonView)
            }
        }
    }

    override fun hide(target: Target?, skeletonId: Int) {
        (target as? ViewTarget)?.apply {
            val parentView = view.getParentViewGroup()
            val skeletonView = parentView.findViewById<View>(skeletonId)
            parentView.removeView(skeletonView)
        }
    }

    private fun generateSkeletonView(skeleton: Skeleton, view: View): View {
        val skeletonView = when (skeleton) {
            is ViewSkeleton -> generateSimpleSkeleton(R.color.colorGray, 6f)
            is TextViewSkeleton -> generateTextViewSkeleton(view as TextView, R.color.colorGray, 6f)
        }
        return skeletonView.validateShimmer(skeleton.isShimmerEnabled, view.getParentViewGroup()) {
            val generatedId = ViewCompat.generateViewId()
            view.skeletonManager.setSkeletonId(generatedId)
            it.id = generatedId
        }
    }

    private fun generateSimpleSkeleton(color: Int, radius: Float): View {
        val skeletonView = View(context)
        skeletonView.background =
            PaintDrawable(context.getColorCompat(color)).apply { setCornerRadius(radius) }
        return skeletonView
    }

    private fun generateTextViewSkeleton(textView: TextView, color: Int, radius: Float): TextView {
        return TextView(context).apply {
            val spannable: Spannable = SpannableString(RandomStringUtils.random(textView.length()))
            spannable.setSpan(
                RoundedBackgroundColorSpan(
                    context.getColorCompat(color),
                    radius
                ), 0, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setTextColor(context.getColorCompat(R.color.colorTransparent))
            text = spannable
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
        val shimmerLayout =
            context.inflate(R.layout.shimmer_layout, parentView) as ShimmerFrameLayout
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