package koleton.mask

import android.graphics.*
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import koleton.util.childViewList

internal class KoletonMask(view: View, @ColorInt maskColor: Int) :
    KoletonMaskable {

    private val bitmap: Bitmap by lazy { Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ALPHA_8) }
    private val canvas: Canvas by lazy { Canvas(bitmap) }
    private val paint: Paint by lazy { Paint().apply { color = maskColor } }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }

    private fun draw(rectF: RectF, cornerRadius: Float, paint: Paint) {
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
    }

    private fun draw(rect: Rect, paint: Paint) {
        canvas.drawRect(rect, paint)
    }

    fun mask(viewGroup: ViewGroup, maskCornerRadius: Float) {
        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = maskCornerRadius > 0
        }
        mask(viewGroup, viewGroup, xferPaint, maskCornerRadius)
    }

    private fun mask(view: View, root: ViewGroup, paint: Paint, maskCornerRadius: Float) {
        (view as? ViewGroup)?.let { viewGroup ->
            viewGroup.childViewList().forEach { view -> mask(view, root, paint, maskCornerRadius) }
        } ?: maskView(view, root, paint, maskCornerRadius)
    }

    private fun maskView(view: View, root: ViewGroup, paint: Paint, maskCornerRadius: Float) {
        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)
        if (maskCornerRadius > 0) {
            val rectF = RectF(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            draw(rectF, maskCornerRadius, paint)
        } else {
            draw(rect, paint)
        }
    }
}