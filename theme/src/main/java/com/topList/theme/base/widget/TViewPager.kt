package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView

/**
 * @author yyf
 * @since 03-11-2020
 */
class TViewPager : ViewPager, IDayNightView {
    private val delegate: AttributeDelegate by lazy {
        AttributeDelegate(this)
    }

    constructor(pContext: Context) : super(pContext)

    @SuppressLint("CustomViewStyleable")
    constructor(pContext: Context, attrs: AttributeSet) : super(pContext, attrs) {
        if (isInEditMode) {
            return
        }
        delegate.save(attrs, defStyleAttr = 0)
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    override fun resetStyle() {
        delegate.resetViewAttr()
        delegate.afterReset()
    }
}