package koleton.target

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import koleton.util.getParentView
import koleton.util.lparams

open class ViewTarget(
    val view: View
) : Target, DefaultLifecycleObserver {

    private var isStarted = false

    override fun onStart() = Unit

    override fun onSuccess(skeleton: View) = setSkeletonView(skeleton)

    override fun onError() = Unit

    override fun onStart(owner: LifecycleOwner) {
        isStarted = true
    }

    override fun onStop(owner: LifecycleOwner) {
        isStarted = false
    }

    protected open fun setSkeletonView(skeleton: View) {
        val parentView = view.getParentView() as ViewGroup
        parentView.addView(skeleton.lparams(view))
    }
}
