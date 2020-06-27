package koleton

import com.facebook.shimmer.Shimmer
import koleton.base.R
import koleton.util.px

/**
 * A set of default options that are used to fill in unset Skeleton values.
 *
 * @see SkeletonLoader.defaults
 */
data class DefaultSkeletonOptions(
    val colorResId: Int = R.color.colorDefault,
    val cornerRadius: Float = CORNER_RADIUS.px,
    val isShimmerEnabled: Boolean = true,
    val itemCount: Int = ITEM_COUNT,
    val lineSpacing: Float = LINE_SPACING.px,
    val shimmer: Shimmer = getDefaultShimmer()
) {
    companion object {
        private const val CORNER_RADIUS = 8
        private const val LINE_SPACING = 4
        private const val ITEM_COUNT = 3
        private const val SHIMMER_DURATION: Long = 1000

        private fun getDefaultShimmer(): Shimmer {
            return Shimmer.AlphaHighlightBuilder()
                .setDuration(SHIMMER_DURATION)
                .setBaseAlpha(0.5f)
                .setHighlightAlpha(0.9f)
                .setWidthRatio(1f)
                .setHeightRatio(1f)
                .setDropoff(1f)
                .build()
        }
    }
}