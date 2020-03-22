package com.topList.android.ui.widget

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * @author yyf
 * @since 02-10-2020
 */
object NormalViewScrollJudge : ViewScrollJudge {

    override fun canScrollUp(view: View, event: MotionEvent, x: Float, y: Float, lockRect: Boolean): Boolean {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                val childLeft = child.left - view.getScrollX()
                val childTop = child.top - view.getScrollY()
                val childRight = child.right - view.getScrollX()
                val childBottom = child.bottom - view.getScrollY()
                val intersects = x > childLeft && x < childRight && y > childTop && y < childBottom
                if ((!lockRect || intersects) && canScrollUp(child, event, x - childLeft, y - childTop, lockRect)) {
                    return true
                }
            }
        }
        return view.canScrollVertically(-1)

    }

    override fun canScrollDown(view: View, event: MotionEvent, x: Float, y: Float, lockRect: Boolean): Boolean {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                val childLeft = child.left - view.getScrollX()
                val childTop = child.top - view.getScrollY()
                val childRight = child.right - view.getScrollX()
                val childBottom = child.bottom - view.getScrollY()
                val intersects = x > childLeft && x < childRight && y > childTop && y < childBottom
                if ((!lockRect || intersects) && canScrollDown(child, event, x - childLeft, y - childTop, lockRect)) {
                    return true
                }
            }
        }

        return view.canScrollVertically(1)
    }

}