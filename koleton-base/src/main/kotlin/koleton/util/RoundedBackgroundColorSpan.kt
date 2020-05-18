package com.ericktijerou.mockplaceholder

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.style.LineBackgroundSpan
import androidx.annotation.ColorInt
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
        rect.set(left, top + verticalPadding, left + width, bottom - verticalPadding)
        canvas.drawRoundRect(RectF(rect), cornerRadius, cornerRadius, paint)
        paint.color = paintColor
    }
}