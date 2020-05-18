package koleton

import android.content.Context
import android.graphics.drawable.PaintDrawable
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ericktijerou.mockplaceholder.RoundedBackgroundColorSpan
import com.facebook.shimmer.ShimmerFrameLayout
import koleton.annotation.ExperimentalKoletonApi
import koleton.base.R
import koleton.skeleton.Skeleton
import koleton.skeleton.TextViewSkeleton
import koleton.skeleton.ViewSkeleton
import koleton.target.ViewTarget
import koleton.util.RandomStringUtils
import koleton.util.getColorCompat
import koleton.util.getParentViewGroup
import koleton.util.inflate

@OptIn(ExperimentalKoletonApi::class)
internal class ViewSkeletonLoader(
    private val context: Context,
    override val defaults: DefaultSkeletonOptions
) : SkeletonLoader {

    companion object {
        private const val TAG = "MainSkeletonLoader"
    }

    override fun execute(skeleton: ViewSkeleton) = with(skeleton) {
        if (target is ViewTarget) {
            val skeletonView = generateSimpleSkeleton(R.color.colorGray, 6f)
            applyPlaceholderAttributes(this, target.view.getParentViewGroup(), skeletonView)
        }
    }

    override fun execute(skeleton: TextViewSkeleton) = with(skeleton) {
        if (target is ViewTarget) {
            val textView = target.view as TextView
            val skeletonView = generateTextViewSkeleton(textView.length(), R.color.colorGray, 6f)
            applyPlaceholderAttributes(this, textView.getParentViewGroup(), skeletonView)
        }
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    private fun generateSimpleSkeleton(color: Int, radius: Float): View {
        val skeletonView = View(context)
        skeletonView.background =
            PaintDrawable(context.getColorCompat(color)).apply { setCornerRadius(radius) }
        return skeletonView
    }

    private fun generateTextViewSkeleton(length: Int, color: Int, radius: Float): TextView {
        return TextView(context).apply {
            val spannable: Spannable = SpannableString(RandomStringUtils.random(length))
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

    private fun createShimmerContainerLayout(parentView: ViewGroup, skeletonView: View): View {
        return createShimmerLayout(parentView).apply {
            addView(skeletonView)
        }
    }

    private fun createShimmerLayout(parentView: ViewGroup): ShimmerFrameLayout {
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

    private fun applyPlaceholderAttributes(skeleton: Skeleton, parentView: ViewGroup, skeletonView: View) {
        val skeletonLayout = if (skeleton.isShimmerEnabled) {
            createShimmerContainerLayout(parentView, skeletonView)
        } else {
            skeletonView
        }
        skeleton.target?.onSuccess(skeletonLayout)
    }
}