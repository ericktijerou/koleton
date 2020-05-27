package koleton.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

internal fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable {
    return checkNotNull(ContextCompat.getDrawable(this, resId)) { "Invalid resource ID: $resId" }
}

internal fun Context.getColorCompat(@ColorRes resId: Int): Int {
    return checkNotNull(ContextCompat.getColor(this, resId)) { "Invalid resource ID: $resId" }
}

internal fun Context.inflate(res: Int, parent: ViewGroup? = null) : View {
    return LayoutInflater.from(this).inflate(res, parent, false)
}

internal fun Context.getLifecycle(): Lifecycle? {
    var context: Context? = this
    while (true) {
        when (context) {
            is LifecycleOwner -> return context.lifecycle
            !is ContextWrapper -> return null
            else -> context = context.baseContext
        }
    }
}