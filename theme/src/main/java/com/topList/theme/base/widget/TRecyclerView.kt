package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.topList.theme.R
import com.topList.theme.ThemeManager
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView

/**
 * @author yyf
 * @since 03-11-2020
 */
open class TRecyclerView : RecyclerView, IDayNightView {
    private val delegate: AttributeDelegate by lazy {
        AttributeDelegate(this)
    }

    constructor(pContext: Context) : super(pContext)

    constructor(pContext: Context, attrs: AttributeSet?) : this(pContext, attrs, 0)

    @SuppressLint("CustomViewStyleable")
    constructor(pContext: Context, attrs: AttributeSet?, styles: Int) : super(pContext, attrs, styles) {
        if (isInEditMode) {
            return
        }
        delegate.save(attrs, defStyleAttr = styles)
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
    }

    override fun resetStyle() {
        try {
            val recycler = RecyclerView::class.java.getDeclaredField("mRecycler")
            recycler.isAccessible = true
            val method = Recycler::class.java.getDeclaredMethod("clear")
            method.isAccessible = true
            method.invoke(recycler[this])
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val recycler = RecyclerView::class.java.getDeclaredField("mRecycler")
            recycler.isAccessible = true
            val method = Recycler::class.java.getDeclaredMethod("clearScrap")
            method.isAccessible = true
            method.invoke(recycler[this])
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        recycledViewPool.clear()
        delegate.resetViewAttr()
        invalidateItemDecorations()
        delegate.afterReset()
    }
}