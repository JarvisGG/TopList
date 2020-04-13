package com.topList.android.ui.widget

import android.content.Context
import com.topList.android.R
import com.topList.theme.dp2px

import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.poovam.pinedittextfield.PinField
import com.poovam.pinedittextfield.Util

class BaseFexLinePinField : PinField {
    private val cursorTopPadding = Util.dpToPx(5f)

    private val cursorBottomPadding = Util.dpToPx(2f)

    var bottomTextPaddingDp = 0f

    var cursorWidth = 1.dp2px.toInt()
        set(value) {
            field = value
            cursorPaint.strokeWidth = field.toFloat()
            invalidate()
        }

    var cursorColor = ContextCompat.getColor(context, R.color.pinFieldLibraryAccent)
        set(value) {
            field = value
            cursorPaint.color = field
            invalidate()
        }

    protected var cursorPaint = Paint()


    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        initParams(attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle) {
        initParams(attr)
    }

    private fun initParams(attr: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attr, R.styleable.BaseFexLinePinField, 0, 0)
        cursorPaint.color = cursorColor
        cursorPaint.strokeWidth = cursorWidth.toFloat()
        try {
            bottomTextPaddingDp =
                a.getDimension(R.styleable.BaseFexLinePinField_bottomTextPaddingDp, bottomTextPaddingDp)
            cursorColor = a.getColor(R.styleable.BaseFexLinePinField_cursorColor, cursorColor)
            cursorWidth = a.getDimensionPixelSize(R.styleable.BaseFexLinePinField_cursorWidth, cursorWidth)
        } finally {
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields) {

            val x1 = (i * singleFieldWidth)
            val padding =
                (if (distanceInBetween != PinField.DEFAULT_DISTANCE_IN_BETWEEN) distanceInBetween else getDefaultDistanceInBetween()) / 2
            val paddedX1 = (x1 + padding)
            val paddedX2 = ((x1 + singleFieldWidth) - padding)
            val paddedY1 = height - yPadding
            val textX = ((paddedX2 - paddedX1) / 2) + paddedX1
            val textY = (paddedY1 - lineThickness) - (textPaint.textSize / 4) - bottomTextPaddingDp
            val character: Char? =
                transformationMethod?.getTransformation(text, this)?.getOrNull(i) ?: text?.getOrNull(i)

            if (highlightAllFields() && hasFocus()) {
                canvas?.drawLine(paddedX1, paddedY1, paddedX2, paddedY1, highlightPaint)
            } else {
                canvas?.drawLine(paddedX1, paddedY1, paddedX2, paddedY1, fieldPaint)
            }

            if (character != null) {
                canvas?.drawText(character.toString(), textX, textY, textPaint)
            }

            if (hasFocus() && i == text?.length ?: 0) {
                if (isCursorEnabled) {
                    val cursorY1 = paddedY1 - cursorBottomPadding - highLightThickness - bottomTextPaddingDp
                    val cursorY2 = cursorTopPadding
                    drawCursor(canvas, textX, cursorY1, cursorY2, cursorPaint)
                }
            }
            highlightLogic(i, text?.length) {
                canvas?.drawLine(paddedX1, paddedY1, paddedX2, paddedY1, highlightPaint)
            }
        }

        if (shouldDrawHint()) {
            hintPaint.textAlign = Paint.Align.CENTER
            canvas?.drawText(hint.toString(), (width / 2).toFloat(), (height / 2).toFloat(), hintPaint)
        }
    }
}