package koleton.custom

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

internal class SkeletonRecyclerView(
    private val recyclerView: RecyclerView,
    @LayoutRes layoutResId: Int,
    itemCount: Int,
    @ColorInt maskColor: Int,
    cornerRadius: Float,
    showShimmer: Boolean,
    @ColorInt shimmerColor: Int,
    shimmerDurationInMillis: Long
) {

    var layoutResId: Int = layoutResId
        set(value) {
            field = value
            invalidate()
        }

    var itemCount: Int = itemCount
        set(value) {
            field = value
            invalidate()
        }

    private val originalAdapter = recyclerView.adapter

    private var skeletonAdapter: SkeletonRecyclerViewAdapter? = null

    init {
        invalidate()
    }

    fun showOriginal() {
        recyclerView.adapter = originalAdapter
    }

    fun showSkeleton() {
        recyclerView.adapter = skeletonAdapter
    }

    fun isSkeleton() = skeletonAdapter != null && recyclerView.adapter == skeletonAdapter

    private fun invalidate() {
        val showSkeleton = isSkeleton()
        skeletonAdapter = SkeletonRecyclerViewAdapter(
            layoutResId,
            itemCount,
            maskColor,
            maskCornerRadius,
            showShimmer,
            shimmerColor,
            shimmerDurationInMillis)
        if (showSkeleton) {
            showSkeleton()
        }
    }
}