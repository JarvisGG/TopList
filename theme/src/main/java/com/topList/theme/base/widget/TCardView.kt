package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView

/**
 * @author yyf
 * @since 03-11-2020
 */
class TCardView : CardView, IDayNightView {
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

    fun setCardBackgroundColorRes(@ColorRes resid: Int) {
        delegate.setRawResId(R.styleable.ThemedView_cardBackgroundColor, resid)
        val color: ColorStateList? = delegate.getColorStateList(R.styleable.ThemedView_cardBackgroundColor, null).data
        super.setCardBackgroundColor(color)
    }

    fun setForegroundResource(@DrawableRes resId: Int) {
        delegate.setRawResId(R.styleable.ThemedView_android_foreground, resId)
        val drawable: Drawable? = delegate.getDrawable(R.styleable.ThemedView_android_foreground).data
        super.setForeground(drawable)
    }

    override fun resetStyle() {
        delegate.resetViewAttr()
        delegate.afterReset()
    }
}