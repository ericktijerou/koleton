package koleton.target

import android.view.View

/**
 * A [Target] with an associated [View]. Prefer this to [Target] if the given skeleton will only be used by [view].
 */
interface ViewTarget<T : View> : Target {

    /**
     * The [View] used by this [Target].
     */
    val view: T
}

