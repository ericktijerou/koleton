package koleton.memory

import androidx.annotation.MainThread
import koleton.SkeletonLoader
import koleton.annotation.ExperimentalKoletonApi
import koleton.skeleton.Skeleton
import koleton.target.ViewTarget
import koleton.util.skeletonManager

@OptIn(ExperimentalKoletonApi::class)
internal class DelegateService(
    private val imageLoader: SkeletonLoader
) {

    fun createTargetDelegate(
        skeleton: Skeleton
    ): TargetDelegate {
        return ViewTargetDelegate(skeleton, skeleton.target)
    }

    @MainThread
    fun createSkeletonDelegate(
        skeleton: Skeleton
    ) {
        val target = skeleton.target as? ViewTarget
        target?.let {
            val skeletonDelegate = ViewTargetSkeletonDelegate(
                imageLoader = imageLoader,
                skeleton = skeleton
            )
            target.view.skeletonManager.setCurrentSkeleton(skeletonDelegate)
        }
    }
}
