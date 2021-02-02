package koleton.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import koleton.mask.KoletonMask
import koleton.util.*

internal class TextKoletonView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : KoletonView(context, attrs, defStyleAttr) {

    private var koletonMask: KoletonMask? = null
    override var isSkeletonShown: Boolean = false
    private var isMeasured: Boolean = false
    private val viewList = arrayListOf<View>()

    var attributes: TextViewAttributes? = null
        set(value) {
            field = value
            originalText = value?.view?.text.toString()
            value?.let { applyAttributes() }
        }

    private var originalText: String = EMPTY_STRING

    private fun hideVisibleChildren(view: View) {
        when (view) {
            is ViewGroup -> view.children().forEach { hideVisibleChildren(it) }
            else -> hideVisibleChild(view)
        }
    }

    private fun hideVisibleChild(view: View) {
        if (view.isVisible()) {
            view.invisible()
            viewList.add(view)
        }
    }

    override fun showSkeleton() {
        isSkeletonShown = true
        setFakeText()
        if (isMeasured && childCount > 0) {
            hideVisibleChildren(this)
            applyAttributes()
        }
    }

    override fun hideSkeleton() {
        isSkeletonShown = false
        restoreOriginalText()
        if (childCount > 0) {
            viewList.forEach { it.visible() }
            hideShimmer()
            koletonMask = null
        }
    }

    private fun setFakeText() {
        attributes?.apply {
            view.text = getRandomAlphaNumericString(length)
        }
    }

    private fun restoreOriginalText() {
        attributes?.apply {
            view.text = originalText
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        isMeasured = width > NUMBER_ZERO && height > NUMBER_ZERO
        if (isSkeletonShown) {
            showSkeleton()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { koletonMask?.draw(it) }
    }

    override fun applyAttributes() {
        if (isMeasured) {
            attributes?.let { attrs ->
                if (!attrs.isShimmerEnabled) {
                    hideShimmer()
                } else {
                    setShimmer(attrs.shimmer)
                }
                koletonMask = KoletonMask(this, attrs.color, attrs.cornerRadius, attrs.lineSpacing)
            }
        }
    }

}