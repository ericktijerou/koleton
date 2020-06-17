package koleton.target

import androidx.annotation.MainThread
import koleton.custom.KoletonView

/**
 * A listener that accepts the result of a view skeleton.
 */
interface Target {

    /**
     * Called when the skeleton starts.
     */
    @MainThread
    fun onStart() {}

    /**
     * Called if the skeleton completes successfully.
     */
    @MainThread
    fun onSuccess(skeleton: KoletonView) {}

    /**
     * Called if an error occurs while loading the skeleton.
     */
    @MainThread
    fun onError() {}
}
