package com.topList.android.ui.feed.behavior

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.topList.android.R

/**
 * @author yyf
 * @since 03-27-2020
 */
class BottomNavigationBehavior(
    val context: Context,
    attributeSet: AttributeSet
) : CoordinatorLayout.Behavior<View>(context, attributeSet) {

    private var deltaY = 0

    private var threshold = 0

    private var isShown = true

    private var animator: ObjectAnimator? = null


    init {
        threshold = context.resources.getDimensionPixelSize(R.dimen.tab_show_scroll_threshold)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {

        deltaY += dy
        if (dy > 0 && deltaY < 0 || dy < 0 && deltaY > 0) {
            deltaY = dy
        }

        if (deltaY < -threshold || deltaY > threshold) {
            if (deltaY < 0 == isShown) {
                return
            }
            startAnim(child, deltaY < 0)
            deltaY = 0
        }
    }

    fun startAnim(child: View, isShown: Boolean) {
        if (isShown == this.isShown) {
            return
        }
        this.isShown = isShown
        animator?.cancel()
        animator = ObjectAnimator.ofFloat(
            child,
            View.TRANSLATION_Y,
            child.translationY,
            if (isShown) 0f else child.height.toFloat()
        ).apply {
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        deltaY = 0
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (deltaY < 0 == isShown) {
            return false
        }

        if (velocityY < 0 == isShown) {
            return false
        } else {
            animator?.cancel()
        }

        isShown = deltaY < 0

        animator = ObjectAnimator.ofFloat(
            child,
            View.TRANSLATION_Y,
            child.translationY,
            if (isShown) 0f else child.height.toFloat()
        ).apply {
            interpolator = DecelerateInterpolator()
            start()
        }
        deltaY = 0

        return false
    }
}