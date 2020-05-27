package koleton.memory

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import koleton.lifecycle.GlobalLifecycle
import koleton.lifecycle.LifecycleCoroutineDispatcher
import koleton.skeleton.Skeleton
import koleton.skeleton.ViewSkeleton
import koleton.target.ViewTarget
import koleton.util.getLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class SkeletonService {

    @MainThread
    fun lifecycleInfo(skeleton: Skeleton): LifecycleInfo {
        when (skeleton) {
            is ViewSkeleton -> {
                val lifecycle = skeleton.getLifecycle()
                return if (lifecycle != null) {
                    val mainDispatcher = LifecycleCoroutineDispatcher
                        .createUnlessStarted(Dispatchers.Main.immediate, lifecycle)
                    LifecycleInfo(lifecycle, mainDispatcher)
                } else {
                    LifecycleInfo.GLOBAL
                }
            }
        }
    }

    private fun ViewSkeleton.getLifecycle(): Lifecycle? {
        return when {
            lifecycle != null -> lifecycle
            target is ViewTarget -> target.view.context.getLifecycle()
            else -> context.getLifecycle()
        }
    }

    data class LifecycleInfo(
        val lifecycle: Lifecycle,
        val mainDispatcher: CoroutineDispatcher
    ) {

        companion object {
            val GLOBAL = LifecycleInfo(
                lifecycle = GlobalLifecycle,
                mainDispatcher = Dispatchers.Main.immediate
            )
        }
    }
}