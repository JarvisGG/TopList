package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat.setTintList
import androidx.core.graphics.drawable.DrawableCompat.wrap
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView
import kotlin.math.roundToInt

/**
 * @author yyf
 * @since 03-11-2020
 */
class TSwitch : SwitchCompat, IDayNightView {

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

    @SuppressLint("ResourceType")
    private fun applyTint() {
        var themeStyle: Int = delegate.getResId(R.styleable.ThemedView_android_theme)
        if (themeStyle <= 0) {
            themeStyle = delegate.getResId(R.styleable.ThemedView_theme)
        }
        if (themeStyle > 0) {
            val attrs = intArrayOf(
                R.attr.colorControlActivated,
                R.attr.colorSwitchThumbNormal,
                android.R.attr.colorForeground
            )
            val a = context.obtainStyledAttributes(themeStyle, attrs)
            try {
                val onColor = a.getColor(0, 0)
                val offColor = a.getColor(1, 0)
                val disableColor = a.getColor(2, 0)
                val states = arrayOf(
                    intArrayOf(-android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_enabled)
                )
                val thumbColors = intArrayOf(offColor, onColor, disableColor)
                val trackColors = intArrayOf(
                    getTrackColor(disableColor, 0.3f),
                    getTrackColor(onColor, 0.3f),
                    getTrackColor(disableColor, 0.3f)
                )
                var drawable: Drawable = wrap(thumbDrawable)
                drawable.state = drawableState
                setTintList(drawable, ColorStateList(states, thumbColors))
                drawable = wrap(trackDrawable)
                drawable.state = drawableState
                setTintList(drawable, ColorStateList(states, trackColors))
            } finally {
                a.recycle()
            }
        }
    }

    private fun getTrackColor(thumbColor: Int, alpha: Float): Int {
        val originalAlpha = Color.alpha(thumbColor)
        return ColorUtils.setAlphaComponent(
            thumbColor,
            (originalAlpha * alpha).roundToInt()
        )
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    override fun resetStyle() {
        delegate.resetTextViewAttr()
        applyTint()
        delegate.afterReset()
    }

}