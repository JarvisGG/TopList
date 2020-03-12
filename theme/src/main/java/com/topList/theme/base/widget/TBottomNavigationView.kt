package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView
import com.topList.theme.base.utils.ResData

/**
 * @author yyf
 * @since 03-11-2020
 */
class TBottomNavigationView : BottomNavigationView, IDayNightView {
    private val delegate: AttributeDelegate by lazy {
        AttributeDelegate(this)
    }

    constructor(pContext: Context) : super(pContext)

    constructor(pContext: Context, attrs: AttributeSet) : this(pContext, attrs, 0)

    @SuppressLint("CustomViewStyleable")
    constructor(pContext: Context, attrs: AttributeSet, styles: Int) : super(pContext, attrs, styles) {
        if (isInEditMode) {
            return
        }
        delegate.save(attrs, defStyleAttr = styles)
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    fun setItemIconTintListRes(@ColorRes tintColorId: Int) {
        delegate.setRawResId(R.styleable.ThemedView_itemIconTint, tintColorId)
        applyItemDrawableColorResource()
    }

    fun setItemTextColorRes(@ColorRes tintColorId: Int) {
        delegate.setRawResId(R.styleable.ThemedView_itemTextColor, tintColorId)
        applyItemTitleResource()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        applyItemDrawableColorResource()
        applyItemTitleResource()
    }

    fun applyItemDrawableColorResource() {
        try {
            val colorTintRes: ResData<ColorStateList?> = delegate.getItemColorStateList(R.styleable.ThemedView_itemIconTint)
            if (colorTintRes.found) {
                itemIconTintList = colorTintRes.data!!
            }
        } catch (e: Exception) {}
    }

    fun applyItemTitleResource() {
        try {
            val colorRes: ResData<ColorStateList?> = delegate.getItemColorStateList(R.styleable.ThemedView_itemTextColor)
            if (colorRes.found) {
                itemTextColor = colorRes.data!!
            }
        } catch (e: Exception) {}

    }

    @SuppressLint("RestrictedApi")
    override fun resetStyle() {
        delegate.resetViewAttr()
        applyItemDrawableColorResource()
        applyItemTitleResource()
        delegate.afterReset()
    }
}