package com.app.memorista.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.app.memorista.R

/**
 * @ClassName: LinedEditText
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/29/2022 11:53 PM
 */
class LinedEditText(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    private var lineColor = ContextCompat.getColor(context, R.color.underline)

    private val mRect = Rect()
    private val mPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = lineColor
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until lineCount) {
            val baseline = getLineBounds(i, mRect).toFloat()
            canvas?.drawLine(
                mRect.left.toFloat(),
                baseline + 1,
                mRect.right.toFloat(),
                baseline + 1,
                mPaint
            )
        }
        super.onDraw(canvas)
    }
}