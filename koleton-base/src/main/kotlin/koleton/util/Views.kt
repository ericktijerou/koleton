package koleton.util

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.constraintlayout.widget.ConstraintLayout

internal fun View.getParentView(): ViewParent {
    return checkNotNull(parent) { "The view has not attach to any view" }
}

internal fun View.getParentViewGroup(): ViewGroup {
    return getParentView() as ViewGroup
}

internal fun <T : View> T.lparams(view: View): T {
    val layoutParams = generateLayoutParams(view.layoutParams).apply {
        width = view.width
        height = view.height
    }
    this@lparams.layoutParams = layoutParams
    return this
}

internal fun generateLayoutParams(layoutParams: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
    return when (layoutParams) {
        is ConstraintLayout.LayoutParams -> ConstraintLayout.LayoutParams(layoutParams)
        else -> ViewGroup.LayoutParams(layoutParams)
    }
}

inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    post { f() }
}