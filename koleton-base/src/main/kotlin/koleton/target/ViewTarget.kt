package koleton.target

import android.view.View

interface ViewTarget<T : View> : Target {

    /**
     * The [View] used by this [Target].
     */
    val view: T
}

