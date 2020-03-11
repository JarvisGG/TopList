package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatButton
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView
import com.topList.theme.base.utils.ResData

/**
 * @author yyf
 * @since 03-11-2020
 */
class TButton : AppCompatButton, IDayNightView {
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

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    override fun setTextAppearance(context: Context?, resId: Int) {
        super.setTextAppearance(context, resId)
        delegate.setRawResId(R.styleable.ThemedView_android_textAppearance, resId)
    }

    override fun setTextAppearance(@StyleRes resid: Int) {
        setTextAppearance(context, resid)
    }

    override fun setCompoundDrawables(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawables(left, top, right, bottom)
        applyDrawableTintColorResource()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        applyDrawableTintColorResource()
    }

    private fun applyDrawableTintColorResource() {
        val colorRes: ResData<ColorStateList?> = delegate.getColorStateList(R.styleable.ThemedView_tintColor)
        if (colorRes.found) {
            val color: Int = colorRes.data!!.getColorForState(drawableState, 0)
            for (drawable in compoundDrawables) {
                drawable?.mutate()?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
            for (drawable in compoundDrawablesRelative) {
                drawable?.mutate()?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun resetStyle() {
        delegate.resetTextViewAttr()
        applyDrawableTintColorResource()
        delegate.afterReset()
    }
}