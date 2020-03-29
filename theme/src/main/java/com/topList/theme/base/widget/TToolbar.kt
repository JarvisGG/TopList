package com.topList.theme.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.topList.theme.R
import com.topList.theme.base.AttributeDelegate
import com.topList.theme.base.iface.IDayNightView

/**
 * @author yyf
 * @since 03-29-2020
 */
class TToolbar : Toolbar, IDayNightView {
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
        applyTintColor()
    }

    private fun applyTintColor() {
        val tintColor: ColorStateList? = getTintColor()
        applyTintColor(tintColor)
    }

    private fun applyTintColor(tintColor: ColorStateList?) {
        if (tintColor != null) {
            setNavigationIconTintColor(tintColor)
            setMenuIconTintColorList(tintColor)
            setMenuTitleColor(tintColor.defaultColor)
            setTitleTextColor(tintColor)
        }
    }

    private fun setMenuTitleColor(color: Int) {
        var menuView: ActionMenuView? = null
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is ActionMenuView) {
                menuView = child
                break
            }
        }
        if (menuView == null) return
        for (i in 0 until menuView.childCount) {
            val child = menuView.getChildAt(i)
            if (child is ActionMenuItemView) {
                child.setTextColor(color)
            }
        }
    }

    private fun setNavigationIconTintColor(tintColor: ColorStateList) {
        val navigationIcon = this.navigationIcon
        if (navigationIcon != null && navigationIcon is TintDrawable) {
            navigationIcon.setTintColor(tintColor)
        }
    }

    private fun setMenuIconTintColorList(tintColor: ColorStateList?) {
        for (i in 0 until super.getMenu().size()) {
            val menuItem = super.getMenu().getItem(i)
            val menuIcon = menuItem.icon
            if (menuIcon != null) {
                if (menuIcon is TintDrawable) {
                    menuIcon.setTintColor(tintColor)
                } else {
                    val newMenuIcon = TintDrawable(menuIcon)
                    newMenuIcon.setTintColor(tintColor)
                    menuItem.icon = newMenuIcon
                    menuIcon.callback = newMenuIcon
                }
                menuIcon.invalidateSelf()
                // 防止动画被停止
                if (menuIcon is Animatable) {
                    if ((menuIcon as Animatable).isRunning) {
                        (menuIcon as Animatable).start()
                    }
                }
            }
        }
    }



    private fun getTintColor(): ColorStateList? {
        return delegate.getColorStateList(R.styleable.ThemedView_tintColor, null).data
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        delegate.setRawResId(R.styleable.ThemedView_android_background, resid)
    }

    override fun resetStyle() {
        delegate.resetViewAttr()
        applyTintColor()
        delegate.afterReset()
    }
}