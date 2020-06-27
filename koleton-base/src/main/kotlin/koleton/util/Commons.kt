package koleton.util

import android.content.res.Resources
import android.os.Looper

internal fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

internal fun Any?.isNull() = this == null

internal fun Any?.isNotNull() = this != null

internal fun Int.isZero() = this == 0

internal fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

@Suppress("UNCHECKED_CAST")
internal inline fun <T> Any.self(block: T.() -> Unit): T {
    this as T
    block()
    return this
}

internal val Int.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

internal val Int.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)