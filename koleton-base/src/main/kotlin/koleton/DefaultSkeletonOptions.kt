package koleton

import com.facebook.shimmer.Shimmer
import koleton.base.R

data class DefaultSkeletonOptions(
    val colorResId: Int = R.color.colorDefault,
    val cornerRadius: Int = 8,
    val isShimmerEnabled: Boolean = true,
    val itemCount: Int = 3,
    val shimmer: Shimmer = Shimmer.AlphaHighlightBuilder().build()
)