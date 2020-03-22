package com.topList.android.ui.widget

import android.view.MotionEvent
import android.view.View

/**
 * @author yyf
 * @since 02-10-2020
 */
interface ViewScrollJudge {

    fun canScrollUp(view: View, event: MotionEvent, x: Float, y: Float, lockRect: Boolean): Boolean

    fun canScrollDown(view: View, event: MotionEvent, x: Float, y: Float, lockRect: Boolean): Boolean
}