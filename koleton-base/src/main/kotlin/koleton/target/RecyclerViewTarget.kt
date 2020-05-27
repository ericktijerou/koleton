package koleton.target

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import koleton.custom.KoletonView

open class RecyclerViewTarget(override val view: RecyclerView) : ViewTarget<RecyclerView>, DefaultLifecycleObserver {

    override fun onStart() = Unit

    override fun onSuccess(skeleton: KoletonView) = skeleton.showSkeleton()

    override fun onError() = Unit

    override fun onStart(owner: LifecycleOwner) {}

    override fun onStop(owner: LifecycleOwner) {}
}