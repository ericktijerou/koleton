package koleton.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.style.LineBackgroundSpan
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

class RoundedBackgroundColorSpan(
    @ColorInt private val color: Int,
    private val cornerRadius: Float,
    private val lineSpacing: Int
) : LineBackgroundSpan {

    private val rect = Rect()

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val paintColor = paint.color
        paint.color = color
        val verticalPadding = (bottom - top) / lineSpacing
        val width = paint.measureText(text, start, end).roundToInt()
        val rightWrapping = left + width
        rect.set(
            left,
            top + verticalPadding,
            if (rightWrapping > right * 0.8) right else rightWrapping,
            bottom - verticalPadding
        )
        paint.isAntiAlias = cornerRadius > NUMBER_ZERO
        canvas.drawRoundRect(RectF(rect), cornerRadius, cornerRadius, paint)
        paint.color = paintColor
    }
}