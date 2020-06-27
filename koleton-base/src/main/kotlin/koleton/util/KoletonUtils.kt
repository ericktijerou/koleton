package koleton.util

import android.view.View

object KoletonUtils {

    /**
     * Hide and cancel any skeleton attached to [view].
     */
    @JvmStatic
    fun hide(view: View) {
        view.koletonManager.hideSkeleton()
    }

    /**
     * @return True if the [view] is hidden by the skeleton.
     */
    @JvmStatic
    fun isSkeletonShown(view: View): Boolean {
        return view.koletonManager.isSkeletonShown()
    }
}