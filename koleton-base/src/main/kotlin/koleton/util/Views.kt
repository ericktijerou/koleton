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
import koleton.custom.RecyclerKoletonView
import koleton.custom.RecyclerViewAttributes
import koleton.custom.SimpleKoletonView
import koleton.memory.ViewTargetSkeletonManager

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

internal fun View.gone() {
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

internal fun View.generateSimpleKoletonView(attributes: Attributes): SimpleKoletonView {
    val parent = parent as? ViewGroup
    return SimpleKoletonView(context).also {
        it.id = id
        it.layoutParams = layoutParams
        parent?.removeView(this)
        it.addView(this.lparams(layoutParams))
        parent?.addView(it)
        it.attributes = attributes
    }
}

internal fun RecyclerView.generateRecyclerKoletonView(attributes: RecyclerViewAttributes): RecyclerKoletonView {
    val parent = parent as? ViewGroup
    return RecyclerKoletonView(context).also {
        it.id = id
        it.layoutParams = layoutParams
        parent?.removeView(this)
        it.addView(this.lparams(layoutParams))
        parent?.addView(it)
        it.attributes = attributes
    }
}

internal fun <T: View> T.lparams(source: ViewGroup.LayoutParams): T {
    val layoutParams = FrameLayout.LayoutParams(source).apply {
        if (width.isZero()) width = this@lparams.width
        if (height.isZero()) height = this@lparams.height
    }
    this@lparams.layoutParams = layoutParams
    return this
}

internal fun View.removeOnGlobalLayoutListener(listener: ViewTreeObserver.OnGlobalLayoutListener) {
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