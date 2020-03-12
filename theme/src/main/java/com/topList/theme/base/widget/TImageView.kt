package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView
import com.topList.theme.base.utils.ResData

/**
 * @author yyf
 * @since 03-12-2020
 */
class TImageView : AppCompatImageView, IDayNightView {

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
        applyDrawableTintColorResource()
    }

    private fun applyDrawableTintColorResource() {
        val colorRes: ResData<ColorStateList?> = delegate.getCompatTint()
        if (colorRes.found) {
            val color: Int = colorRes.data!!.getColorForState(drawableState, 0)
            val drawable = this.drawable
            drawable?.mutate()?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        delegate.setRawResId(R.styleable.ThemedView_android_src, resId)
        applyDrawableTintColorResource()
    }

    fun setTintColorResource(@ColorRes tintColorId: Int) {
        delegate.setRawResId(R.styleable.ThemedView_tintColor, tintColorId)
        delegate.setRawResId(R.styleable.ThemedView_android_tint, tintColorId)
        applyDrawableTintColorResource()
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    override fun resetStyle() {
        delegate.resetViewAttr()
        val resId: Int = delegate.getResId(R.styleable.ThemedView_android_src)
        if (resId > 0) {
            setImageResource(resId)
        }
        applyDrawableTintColorResource()
        delegate.afterReset()
    }

}