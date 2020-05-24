package koleton.target

import android.view.View
import android.view.ViewGroup
import koleton.util.getParentView
import koleton.util.lparams

open class ViewTarget(
    val view: View
) : Target {

    override fun onStart() = Unit

    override fun onSuccess(skeleton: View) = setSkeletonView(skeleton)

    override fun onError() = Unit

    protected open fun setSkeletonView(skeleton: View) {
        val parentView = view.getParentView() as ViewGroup
        parentView.addView(skeleton.lparams(view))
    }
}
