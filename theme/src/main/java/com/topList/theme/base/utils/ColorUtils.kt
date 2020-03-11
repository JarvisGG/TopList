package com.topList.theme.base.utils

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * @author yyf
 * @since 03-11-2020
 */
object ColorUtils {
    /**
     * 移除颜色的透明
     *
     * @param color 带透明的颜色
     * @return 去除透明的颜色
     */
    fun removeAlpha(color: Int): Int {
        return -0x1000000 or color
    }

    /**
     * 给颜色设置透明度
     *
     * @param alpha 透明度，最大255
     * @return 设置好指定透明度的颜色
     */
    fun setAlpha(color: Int, alpha: Int): Int {
        return Color.argb(
            alpha,
            Color.red(color),
            Color.green(color),
            Color.blue(color)
        )
    }

    /**
     * 给颜色设置透明度
     *
     * @param factor 透明度，最大 1
     * @return 设置好指定透明度的颜色
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        return setAlpha(
            color,
            Math.round(Color.alpha(color) * factor)
        )
    }

    /**
     * 合并两个颜色
     *
     * @param backgroundColor 背景色
     * @param foregroundColor 前景色
     * @return 合并之后的颜色
     */
    fun mergeColors(backgroundColor: Int, foregroundColor: Int): Int {
        val ALPHA_CHANNEL: Byte = 24
        val RED_CHANNEL: Byte = 16
        val GREEN_CHANNEL: Byte = 8
        val BLUE_CHANNEL: Byte = 0
        val ap1 =
            (backgroundColor shr ALPHA_CHANNEL.toInt() and 0xff).toDouble() / 255.0
        val ap2 =
            (foregroundColor shr ALPHA_CHANNEL.toInt() and 0xff).toDouble() / 255.0
        val ap = ap2 + ap1 * (1 - ap2)
        val amount1 = ap1 * (1 - ap2) / ap
        val amount2 = amount1 / ap
        val a = (ap * 255.0).toInt() and 0xff
        val r =
            ((backgroundColor shr RED_CHANNEL.toInt() and 0xff).toFloat() * amount1 + (foregroundColor shr
                    RED_CHANNEL.toInt() and 0xff).toFloat() * amount2).toInt() and 0xff
        val g =
            ((backgroundColor shr GREEN_CHANNEL.toInt() and 0xff).toFloat() * amount1 + (foregroundColor shr
                    GREEN_CHANNEL.toInt() and 0xff).toFloat() * amount2).toInt() and 0xff
        val b =
            (((backgroundColor and 0xff).toFloat() * amount1 + (foregroundColor and 0xff).toFloat() * amount2).toInt()
                    and 0xff)
        return a shl ALPHA_CHANNEL.toInt() or (r shl RED_CHANNEL.toInt()) or (g shl GREEN_CHANNEL.toInt()) or (b shl BLUE_CHANNEL.toInt())
    }

    /**
     * 获取 StatusBar 颜色
     *
     * @param baseColor 参考颜色
     * @return 参考颜色混合 20% 的黑色
     */
    @ColorInt
    fun getStatusBarColor(@ColorInt baseColor: Int): Int {
        return mergeColors(
            baseColor,
            setAlpha(Color.BLACK, (0.2f * 255).toInt())
        )
    }
}
