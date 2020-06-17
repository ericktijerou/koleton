package koleton.util

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import koleton.base.R
import koleton.custom.Attributes
import koleton.custom.KoletonFrameLayout
import koleton.custom.KoletonRecyclerView
import koleton.custom.RecyclerViewAttributes
import koleton.memory.ViewTargetSkeletonManager

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
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

internal fun ViewGroup.children(): List<View> {
    return (0 until childCount).map { child -> getChildAt(child) }
}

internal fun View.generateKoletonFrameLayout(attributes: Attributes): KoletonFrameLayout {
    val parent = parent as? ViewGroup
    return KoletonFrameLayout(context).also {
        it.id = id
        it.layoutParams = layoutParams
        parent?.removeView(this)
        it.addView(this.lparams(layoutParams))
        parent?.addView(it)
        it.attributes = attributes
    }
}

internal fun RecyclerView.generateKoletonRecyclerView(attributes: RecyclerViewAttributes): KoletonRecyclerView {
    val parent = parent as? ViewGroup
    return KoletonRecyclerView(context).also {
        it.id = id
        it.layoutParams = layoutParams
        parent?.removeView(this)
        it.addView(this.lparams(layoutParams))
        parent?.addView(it)
        it.attributes = attributes
    }
}

fun <T: View> T.lparams(source: ViewGroup.LayoutParams): T {
    val layoutParams = FrameLayout.LayoutParams(source).apply {
        if (width.isZero() || height.isZero()) {
            width = this@lparams.width
            height = this@lparams.height
        }
    }
    this@lparams.layoutParams = layoutParams
    return this
}

fun View.removeOnGlobalLayoutListener(listener: ViewTreeObserver.OnGlobalLayoutListener) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        @Suppress("DEPRECATION")
        this.viewTreeObserver.removeGlobalOnLayoutListener(listener)
    } else {
        this.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
}

internal val View.koletonManager: ViewTargetSkeletonManager
    get() {
        var manager = getTag(R.id.koleton_manager) as? ViewTargetSkeletonManager
        if (manager == null) {
            manager = ViewTargetSkeletonManager().apply {
                viewTreeObserver.addOnGlobalLayoutListener(this)
                setTag(R.id.koleton_manager, this)
            }
        }
        return manager
    }