package koleton.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import koleton.base.R
import koleton.mask.KoletonMask
import koleton.util.getColorCompat
import koleton.util.invisible
import koleton.util.childViewList
import koleton.util.visible

internal class KoletonFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    view: View? = null
) : FrameLayout(context, attrs, defStyleAttr) {

    private var koletonMask: KoletonMask? = null
    private var isSkeletonShown: Boolean = false
    private var isRendered: Boolean = false

    internal constructor(view: View) : this(view.context, null, 0, view)

    fun showSkeleton() {
        isSkeletonShown = true
        if (isRendered && childCount > 0) {
            childViewList().forEach { it.invisible() }
            setWillNotDraw(false)
            invalidateMask()
            koletonMask?.invalidate()
        }
    }

    fun hideSkeleton() {
        isSkeletonShown = false
        if (childCount > 0) {
            childViewList().forEach { it.visible() }
            koletonMask?.stop()
            koletonMask = null
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        isRendered = true
        if (isSkeletonShown) {
            showSkeleton()
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        koletonMask?.invalidate()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        when (hasWindowFocus) {
            true -> koletonMask?.start()
            false -> koletonMask?.stop()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isRendered) {
            invalidateMask()
            koletonMask?.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        koletonMask?.stop()
    }

    override fun onDraw(canvas: Canvas) {
        koletonMask?.draw(canvas)
    }

    private fun invalidateMask() {
        if (isRendered) {
            koletonMask?.stop()
            if (width > 0 && height > 0) {
                koletonMask = KoletonMask(
                    this,
                    context.getColorCompat(R.color.colorGray)
                )
                    .also { mask -> mask.mask(this, 8f) }
            }
        }
    }
}