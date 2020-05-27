package com.ericktijerou.mockplaceholder

import android.graphics.*
import android.text.style.LineBackgroundSpan
import androidx.annotation.ColorInt
import koleton.util.NUMBER_ZERO
import kotlin.math.roundToInt


class RoundedBackgroundColorSpan(
    @ColorInt private val color: Int,
    private val cornerRadius: Float
) : LineBackgroundSpan {

    companion object {
        private const val LINE_SPACING_DIVISOR = 8
    }

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
        val verticalPadding = (bottom - top) / LINE_SPACING_DIVISOR
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