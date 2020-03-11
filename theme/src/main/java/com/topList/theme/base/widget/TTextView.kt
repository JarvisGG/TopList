package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Layout
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView
import com.topList.theme.base.utils.ResData
import com.topList.theme.base.utils.SystemUtils

/**
 * @author yyf
 * @since 03-11-2020
 */
class TTextView : AppCompatTextView, IDayNightView {

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
        init()
    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.M)
    private fun init() { // 在 M 上开启更加先进的断字设置
        if (SystemUtils.SDK_VERSION_M_OR_LATER) {
            this.breakStrategy = Layout.BREAK_STRATEGY_HIGH_QUALITY
            this.hyphenationFrequency = Layout.HYPHENATION_FREQUENCY_FULL
        }
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

    fun setTextColorRes(@ColorRes resId: Int) {
        super.setTextColor(ContextCompat.getColor(context, resId))
        delegate.setRawResId(R.styleable.ThemedView_android_textColor, resId)
    }

    fun setDrawableTintColorResource(@ColorRes tintColorId: Int) {
        delegate.setRawResId(R.styleable.ThemedView_tintColor, tintColorId)
        applyDrawableTintColorResource()
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
        try {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun resetStyle() {
        delegate.resetTextViewAttr()
        applyDrawableTintColorResource()
        delegate.afterReset()
    }
}