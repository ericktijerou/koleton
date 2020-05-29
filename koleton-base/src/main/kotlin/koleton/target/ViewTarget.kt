package koleton.target

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import koleton.util.getParentViewGroup

open class ViewTarget(
    val view: View
) : Target, DefaultLifecycleObserver {

    override fun onStart() = Unit

    override fun onSuccess(skeleton: View) = setSkeletonView(skeleton)

    override fun onError() = Unit

    protected open fun setSkeletonView(skeleton: View) {
        //view.getParentViewGroup().addView(skeleton)
    }

    override fun onStart(owner: LifecycleOwner) {}

    override fun onStop(owner: LifecycleOwner) {}
}
