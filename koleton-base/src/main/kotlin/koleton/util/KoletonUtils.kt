package koleton.util

import android.view.View
import koleton.annotation.ExperimentalKoletonApi

object KoletonUtils {

    @ExperimentalKoletonApi
    @JvmStatic
    fun hide(view: View) {
        view.afterMeasured { it.skeletonManager.hideSkeleton() }
    }
}