package koleton.memory

import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import koleton.SkeletonLoader
import koleton.skeleton.ViewSkeleton
import koleton.target.ViewTarget
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

internal sealed class SkeletonDelegate : DefaultLifecycleObserver {

    @MainThread
    open fun dispose() {}

    @MainThread
    open fun onComplete() {}

    @MainThread
    abstract fun getViewTarget(): View?
}

internal class ViewTargetSkeletonDelegate(
    private val imageLoader: SkeletonLoader,
    private val skeleton: ViewSkeleton,
    private val target: TargetDelegate,
    private val lifecycle: Lifecycle,
    private val dispatcher: CoroutineDispatcher,
    private val job: Job
): SkeletonDelegate() {

    @MainThread
    fun hideSkeleton(skeletonId: Int) {
        imageLoader.hide(skeleton.target, skeletonId)
    }

    @MainThread
    fun restart() {
        imageLoader.execute(skeleton)
    }

    override fun dispose() {
        job.cancel()
        target.clear()

        if (skeleton.target is LifecycleObserver) {
            lifecycle.removeObserver(skeleton.target)
        }
        lifecycle.removeObserver(this)
    }


    override fun getViewTarget(): View? {
        val target = skeleton.target as? ViewTarget
        return target?.view
    }

    override fun onComplete() {
        if (dispatcher is LifecycleObserver) {
            lifecycle.removeObserver(dispatcher)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) = dispose()
}
