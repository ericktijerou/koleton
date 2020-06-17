package koleton.util

import android.view.View
import koleton.annotation.ExperimentalKoletonApi

object KoletonUtils {

    /**
     * Hide and cancel any skeleton attached to [view]}.
     */
    @JvmStatic
    fun hide(view: View) {
        view.koletonManager.hideSkeleton()
    }
}