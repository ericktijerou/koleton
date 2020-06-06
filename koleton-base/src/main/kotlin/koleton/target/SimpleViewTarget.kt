package koleton.target

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import koleton.custom.KoletonView

open class SimpleViewTarget(override val view: View) : ViewTarget<View>,
    DefaultLifecycleObserver {

    override fun onStart() = Unit

    override fun onSuccess(skeleton: KoletonView) = skeleton.showSkeleton()

    override fun onError() = Unit

    override fun onStart(owner: LifecycleOwner) {}

    override fun onStop(owner: LifecycleOwner) {}
}