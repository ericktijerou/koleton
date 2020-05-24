package koleton.memory

import androidx.annotation.MainThread
import koleton.SkeletonLoader
import koleton.skeleton.Skeleton

internal class ViewTargetSkeletonDelegate(
    private val imageLoader: SkeletonLoader,
    private val skeleton: Skeleton
) {

    @MainThread
    fun hideSkeleton(skeletonId: Int) {
        imageLoader.hide(skeleton.target, skeletonId)
    }
}
