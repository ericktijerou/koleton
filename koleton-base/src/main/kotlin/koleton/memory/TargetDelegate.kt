@file:OptIn(ExperimentalKoletonApi::class)

package koleton.memory

import androidx.annotation.MainThread
import koleton.annotation.ExperimentalKoletonApi
import koleton.custom.KoletonView
import koleton.skeleton.Skeleton
import koleton.target.Target

internal sealed class TargetDelegate {

    @MainThread
    open fun start() {}

    @MainThread
    open fun success(skeleton: KoletonView) {}

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

    override fun success(skeleton: KoletonView) {
        target?.onSuccess(skeleton)
    }

    override fun error() {
        target?.onError()
    }

    override fun clear() {}
}