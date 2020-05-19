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

    override fun execute(skeleton: Skeleton) {
        val target = skeleton.target as? ViewTarget
        target?.apply {
            val skeletonView = when (skeleton) {
                is ViewSkeleton -> generateSimpleSkeleton(R.color.colorGray, 6f)
                is TextViewSkeleton -> generateTextViewSkeleton(view as TextView, R.color.colorGray, 6f)
            }
            onSuccess(applyShimmer(skeleton.isShimmerEnabled, skeletonView, view.getParentViewGroup()))
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

    private fun applyShimmer(isShimmerEnabled: Boolean, skeletonView: View, parentView: ViewGroup? = null): View {
        return if (isShimmerEnabled) {
            generateShimmerLayout(parentView).apply { addView(skeletonView) }
        } else {
            skeletonView
        }
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