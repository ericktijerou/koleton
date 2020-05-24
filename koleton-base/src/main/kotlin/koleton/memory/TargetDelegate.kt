@file:OptIn(ExperimentalKoletonApi::class)

package koleton.memory

import android.view.View
import androidx.annotation.MainThread
import koleton.annotation.ExperimentalKoletonApi
import koleton.skeleton.Skeleton
import koleton.target.Target

internal sealed class TargetDelegate {

    @MainThread
    open fun start() {}

    @MainThread
    open fun success(skeletonView: View) {}

    @MainThread
    open fun error() {}

    @MainThread
    open fun clear() {}
}

internal class ViewTargetDelegate(
    private val skeleton: Skeleton,
    private val target: Target?
) : TargetDelegate() {

    override fun start() {
        target?.onStart()
    }

    override fun success(skeletonView: View) {
        target?.onSuccess(skeletonView)
    }

    override fun error() {
        target?.onError()
    }

    override fun clear() {}
}