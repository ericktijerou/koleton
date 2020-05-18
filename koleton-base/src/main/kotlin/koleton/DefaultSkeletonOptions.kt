package koleton

import koleton.base.R

data class DefaultSkeletonOptions(
    val colorResId: Int = R.color.colorGray,
    val borderRadiusId: Int = R.dimen.skeleton_radius,
    val isShimmerEnabled: Boolean = true
)