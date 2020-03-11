package com.topList.theme.base.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.topList.theme.R
import com.topList.theme.ThemeManager

/**
 * @author yyf
 * @since 03-11-2020
 */
object NavigationBarUtil {

    fun setNavigationBarColor(activity: Activity, @ColorInt color: Int) {
        if (SystemUtils.SDK_VERSION_LOLLIPOP_OR_LATER) {
            activity.window.navigationBarColor = color
        }
    }

    fun setDefaultNavigationBarColor(activity: Activity) {
        if (SystemUtils.isMIUI() || SystemUtils.SDK_VERSION_O_MR1_OR_LATER) { // 小米（小米只有 MIX 系列有虚拟导航键）和原生 8.1+ 开始，底栏为灰色。所以使用纯色的看起来更好一点
            setNavigationBarColor(activity, ContextCompat.getColor(activity, R.color.navigation_bar_color_pure))
            setLightNavigationButton(activity, ThemeManager.isLight())
            return
        }
        if (SystemUtils.isHuaweiEMUI()) {
            setNavigationBarColor(activity, Color.BLACK)
        } else {
            setNavigationBarColor(activity, ColorUtils.getStatusBarColor(ContextCompat.getColor(activity, R.color.navigation_bar_color)))
        }
    }

    private fun setLightNavigationButton(activity: Activity, light: Boolean) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (SystemUtils.SDK_VERSION_O_OR_LATER) {
            var flag = view.systemUiVisibility
            flag = if (light) {
                flag or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                flag and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            view.systemUiVisibility = flag
        }
    }


}