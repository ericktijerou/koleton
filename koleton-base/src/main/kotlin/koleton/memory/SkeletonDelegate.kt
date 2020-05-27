package koleton.memory

import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import koleton.SkeletonLoader
import koleton.custom.KoletonView
import koleton.skeleton.Skeleton
import koleton.skeleton.ViewSkeleton
import koleton.target.ViewTarget
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

internal sealed class SkeletonDelegate : DefaultLifecycleObserver {

    @MainThread
    open fun dispose() {}

    @MainThread
    open fun onComplete() {}
}

internal class ViewTargetSkeletonDelegate(
    private val imageLoader: SkeletonLoader,
    private val skeleton: Skeleton,
    private val target: TargetDelegate,
    private val lifecycle: Lifecycle,
    private val dispatcher: CoroutineDispatcher,
    private val job: Job
): SkeletonDelegate() {

    @MainThread
    fun hideSkeleton(koletonView: KoletonView) {
        imageLoader.hide(skeleton.target, koletonView)
    }

    @MainThread
    fun restart() {
        imageLoader.execute(skeleton)
    }

    override fun dispose() {
        job.cancel()
        target.clear()
        val skeletonTarget = skeleton.target
        if (skeletonTarget is ViewTarget<*> && skeletonTarget is LifecycleObserver) {
            lifecycle.removeObserver(skeletonTarget)
        }
        lifecycle.removeObserver(this)
    }

    @MainThread
    fun getViewTarget(): View? {
        val target = skeleton.target as? ViewTarget<*>
        return target?.view
    }

    override fun onComplete() {
        if (dispatcher is LifecycleObserver) {
            lifecycle.removeObserver(dispatcher)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) = dispose()
}

internal class BaseRequestDelegate(
    private val lifecycle: Lifecycle,
    private val dispatcher: CoroutineDispatcher,
    private val job: Job
) : SkeletonDelegate() {

    override fun dispose() = job.cancel()

    override fun onComplete() {
        if (dispatcher is LifecycleObserver) {
            lifecycle.removeObserver(dispatcher)
        }
        lifecycle.removeObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) = dispose()
}