package koleton.custom

import android.content.Context
import android.util.AttributeSet
import com.facebook.shimmer.ShimmerFrameLayout

abstract class KoletonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr) {

    abstract var isSkeletonShown: Boolean

    abstract fun hideSkeleton()

    abstract fun showSkeleton()

    protected abstract fun applyAttributes()
}