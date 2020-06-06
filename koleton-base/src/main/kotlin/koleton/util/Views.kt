package koleton.util

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import koleton.DefaultSkeletonOptions
import koleton.base.R
import koleton.custom.*
import koleton.custom.KoletonFrameLayout
import koleton.custom.KoletonRecyclerView
import koleton.memory.ViewTargetSkeletonManager
import koleton.skeleton.ViewSkeleton
import koleton.target.SimpleViewTarget
import koleton.target.ViewTarget

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