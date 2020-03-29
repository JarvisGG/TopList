package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.appcompat.graphics.drawable.DrawableWrapper

/**
 * @author yyf
 * @since 03-29-2020
 */
@SuppressLint("RestrictedApi")
class TintDrawable(pDrawable: Drawable) :
    DrawableWrapper(pDrawable.mutate()) {
    private var mTintColorStateList: ColorStateList? = null
    fun setTintColorRes(pResources: Resources, @ColorRes pTintColorRes: Int) {
        setTintColor(pResources.getColorStateList(pTintColorRes))
    }

    fun setTintColor(pColorStateList: ColorStateList?) {
        if (pColorStateList == null) {
            return
        }
        mTintColorStateList = pColorStateList
        this.setColorFilter(
            mTintColorStateList!!.getColorForState(
                this.state, mTintColorStateList!!
                    .defaultColor
            ), PorterDuff.Mode.SRC_IN
        )
    }

    override fun onStateChange(pState: IntArray?): Boolean {
        if (mTintColorStateList != null) {
            super.setColorFilter(
                mTintColorStateList!!.getColorForState(
                    pState, mTintColorStateList!!
                        .defaultColor
                ), PorterDuff.Mode.SRC_IN
            )
        }
        return super.onStateChange(pState)
    }

    override fun setState(stateSet: IntArray): Boolean {
        return if (!wrappedDrawable.state.contentEquals(stateSet)) {
            onStateChange(stateSet) && super.setState(stateSet)
        } else super.setState(stateSet)
    }

    override fun isStateful(): Boolean {
        return if (mTintColorStateList != null) {
            mTintColorStateList!!.isStateful
        } else {
            super.isStateful()
        }
    }

    override fun setWrappedDrawable(pDrawable: Drawable?) {
        if (pDrawable != null && pDrawable.callback != null) {
            callback = pDrawable.callback
        }
        super.setWrappedDrawable(pDrawable)
    }

    init {
        this.bounds = pDrawable.bounds
    }
}