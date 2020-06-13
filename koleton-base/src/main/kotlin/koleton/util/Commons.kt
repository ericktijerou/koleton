package koleton.util

import android.content.res.Resources
import android.os.Looper

internal fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun Any?.isNull() = this == null

fun Any?.isNotNull() = this != null

fun Int.isZero() = this == 0

fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

@Suppress("UNCHECKED_CAST")
internal inline fun <T> Any.self(block: T.() -> Unit): T {
    this as T
    block()
    return this
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()