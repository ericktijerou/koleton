package koleton.target

import android.view.View
import androidx.annotation.MainThread

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
    fun onSuccess(skeleton: View) {}

    /**
     * Called if an error occurs while executing the skeleton.
     */
    @MainThread
    fun onError() {}
}
