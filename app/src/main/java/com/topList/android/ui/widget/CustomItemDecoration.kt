package com.topList.android.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.topList.android.R
import dp2px
import kotlin.math.roundToInt

/**
 * @author yyf
 * @since 03-12-2020
 */
class CustomItemDecoration(
    private val context: Context,
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    },
    private val dividerColorRes: Int = R.color.GBK08A,
    private val dividerHeight: Float = 0.5f.dp2px
) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        paint.color = ContextCompat.getColor(context, dividerColorRes)
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount - 1) {
            val child: View = parent.getChildAt(i)
            val bottom = child.bottom + child.translationY.roundToInt()
            c.drawRect(left.toFloat(), bottom.toFloat(), right.toFloat(), bottom + dividerHeight, paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect[0, 0, 0] = dividerHeight.toInt()
    }
}
