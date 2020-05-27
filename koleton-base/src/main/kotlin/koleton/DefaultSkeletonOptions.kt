package koleton

import koleton.base.R

data class DefaultSkeletonOptions(
    val colorResId: Int = R.color.colorGray,
    val cornerRadius: Int = 8,
    val isShimmerEnabled: Boolean = true,
    val itemCount: Int = 3
)