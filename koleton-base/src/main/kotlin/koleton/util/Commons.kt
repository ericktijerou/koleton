package koleton.util

import android.os.Looper
import android.view.View
import koleton.base.R
import koleton.memory.ViewTargetSkeletonManager

internal val View.skeletonManager: ViewTargetSkeletonManager
    get() {
        var manager = getTag(R.id.koleton_manager) as? ViewTargetSkeletonManager
        if (manager == null) {
            manager = ViewTargetSkeletonManager().apply {
                addOnAttachStateChangeListener(this)
                viewTreeObserver.addOnGlobalLayoutListener(this)
                setTag(R.id.koleton_manager, this)
            }
        }
        return manager
    }

internal fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun Any?.isNull() = this == null

fun Any?.isNotNull() = this != null

fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}