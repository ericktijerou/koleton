package koleton.util

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

internal fun View.getParentView(): ViewParent {
    return checkNotNull(parent) { "The view has not attach to any view" }
}

internal fun View.getParentViewGroup(): ViewGroup {
    return getParentView() as ViewGroup
}

internal fun <T : View> T.lparams(view: View): T {
    val layoutParams = generateLayoutParams(view.layoutParams)
    this@lparams.layoutParams = layoutParams
    return this
}

internal fun generateLayoutParams(layoutParams: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
    return when (layoutParams) {
        is ConstraintLayout.LayoutParams -> ConstraintLayout.LayoutParams(layoutParams)
        else -> ViewGroup.LayoutParams(layoutParams)
    }
}

internal fun ViewGroup.cloneLayout(): ViewGroup {
    return when (this) {
        is ConstraintLayout -> ConstraintLayout(context)
        else -> FrameLayout(context)
    }
}

internal fun ViewGroup.childViewList(): List<View> {
    return (0 until childCount).map { child -> getChildAt(child) }
}