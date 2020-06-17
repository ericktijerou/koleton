package koleton

import com.facebook.shimmer.Shimmer
import koleton.base.R

/**
 * A set of default options that are used to fill in unset Skeleton values.
 *
 * @see SkeletonLoader.defaults
 */
data class DefaultSkeletonOptions(
    val colorResId: Int = R.color.colorDefault,
    val cornerRadius: Int = 8,
    val isShimmerEnabled: Boolean = true,
    val itemCount: Int = 3,
    val shimmer: Shimmer = Shimmer.AlphaHighlightBuilder().build(),
    val lineSpacing: Int = 8
)