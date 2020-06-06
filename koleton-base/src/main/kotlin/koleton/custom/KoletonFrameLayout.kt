package koleton.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.ShimmerFrameLayout
import koleton.mask.KoletonMask
import koleton.util.*

internal class KoletonFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr), KoletonView {

    private var koletonMask: KoletonMask? = null
    private var isSkeletonShown: Boolean = false
    private var isMeasured: Boolean = false
    private val viewList = arrayListOf<View>()

    var attributes: Attributes? = null
        set(value) {
            field = value
            value?.let { applyAttributes() }
        }

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
        if (isMeasured && childCount > 0) {
            hideVisibleChildren(this)
            applyAttributes()
        }
    }

    override fun hideSkeleton() {
        isSkeletonShown = false
        if (childCount > 0) {
            viewList.forEach { it.visible() }
            hideShimmer()
            koletonMask = null
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        isMeasured = true
        if (isSkeletonShown) {
            showSkeleton()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { koletonMask?.draw(it) }
    }

    private fun applyAttributes() {
        if (isMeasured) {
            if (attributes !is SimpleViewAttributes) hideShimmer()
            attributes?.let { attrs ->
                koletonMask = KoletonMask(this, attrs.color, attrs.cornerRadius.toFloat())
            }
        }
    }

}