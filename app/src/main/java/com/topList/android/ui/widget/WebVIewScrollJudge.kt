package com.topList.android.ui.widget

import android.view.MotionEvent
import android.view.View
import com.topList.android.ui.widget.ViewScrollJudge

/**
 * @author yyf
 * @since 02-10-2020
 */
object WebVIewScrollJudge : ViewScrollJudge {
    override fun canScrollUp(view: View, event: MotionEvent, x: Float, y: Float, lockRect: Boolean): Boolean {
//        if (mWebViewContentHeight == 0) {
//            mWebViewContentHeight = WebViewApi.computeVerticalScrollRange(view)
//        }
//        val offset = WebViewApi.getScrollY(view)
//        val range = mWebViewContentHeight - WebViewApi.getHeight(view)
//        return if (range == 0) {
//            false
//        } else offset > 2

        return false
    }

    override fun canScrollDown(view: View, event: MotionEvent, x: Float, y: Float, lockRect: Boolean): Boolean {
//        if (mWebViewContentHeight == 0) {
//            mWebViewContentHeight = WebViewApi.computeVerticalScrollRange(view)
//        }
//        val offset = WebViewApi.getScrollY(view)
//        val range = mWebViewContentHeight - WebViewApi.getHeight(view)
//        return if (range == 0) {
//            false
//        } else offset < range - 2

        return false
    }

}