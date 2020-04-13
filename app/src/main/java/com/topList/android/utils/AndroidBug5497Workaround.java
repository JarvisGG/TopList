package com.topList.android.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * @author yyf
 * @since 04-13-2020
 * @link <a href="https://stackoverflow.com/questions/7417123/android-how-to-adjust-layout-in-full-screen-mode-when-softkeyboard-is-visible"/>
 */
public class AndroidBug5497Workaround implements ViewTreeObserver.OnGlobalLayoutListener {
    private View mChildOfContent;
    private FrameLayout.LayoutParams mFrameLayoutParams;
    private int mDisplayHeightPrevious;


    public static void assistActivity (Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private AndroidBug5497Workaround(Activity activity) {
        final FrameLayout content = activity.findViewById(android.R.id.content);
        if (content != null) {
            mChildOfContent = content.getChildAt(0);
            mFrameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    @Override
    public void onGlobalLayout() {
        if (mChildOfContent == null) return;

        final Rect displayRect = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(displayRect);

        if (displayRect.bottom != mDisplayHeightPrevious) {
            mFrameLayoutParams.height = displayRect.bottom;
            mChildOfContent.requestLayout();
            mDisplayHeightPrevious = displayRect.bottom;
        }
    }
}