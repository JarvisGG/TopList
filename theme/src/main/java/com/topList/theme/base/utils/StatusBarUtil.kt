package com.topList.theme.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.topList.theme.ThemeManager
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * @author yyf
 * @since 03-11-2020
 */
object StatusBarUtil {


    /**
     * 使 Activity 的 StatusBar 变透明
     */
    fun translucentStatusBar(@NonNull activity: Activity) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        // 全屏
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.x
            cleanStatusBarBackgroundColor(activity)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // 设置 FitsSystemWindow 为 False，不预留 StatusBar 位置
        val contentView =
            activity.window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val contentChild = contentView.getChildAt(0)
        contentChild?.fitsSystemWindows = true
    }

    /**
     * 去除 StatusBar 的颜色
     */
    fun cleanStatusBarBackgroundColor(@NonNull activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.x
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 添加 FakeStatusBar Drawable 到 View 的背景中
     */
    fun addStatusBarDrawableToView(@NonNull rootView: View, color: Int): StatusBarDrawable? {
        val backgroud = rootView.background
        return if (backgroud !is CombinedDrawable) {
            val context = rootView.context
            val statusBarDrawable = StatusBarDrawable(color, getStatusBarHeight(context))
            rootView.background = CombinedDrawable(backgroud, statusBarDrawable)
            rootView.setPadding(
                rootView.paddingLeft,
                rootView.paddingTop + getStatusBarHeight(context),
                rootView.paddingRight,
                rootView.paddingBottom
            )
            statusBarDrawable
        } else {
            backgroud.statusBarDrawable
        }
    }


    /**
     * 从 View 的背景中清除 FakeStatusBar Drawable
     */
    fun removeStatusBarDrawableFromView(rootView: View) {
        val background = rootView.background
        if (background is CombinedDrawable) {
            rootView.background =
                background.origin
            rootView.setPadding(
                rootView.paddingLeft,
                rootView.paddingTop - getStatusBarHeight(rootView.context),
                rootView.paddingRight,
                rootView.paddingBottom
            )
        }
    }


    /**
     * 设置 StatusBar 的文本颜色为黑色
     * @link [](http://www.jianshu.com/p/932568ed31af)
     */
    fun setStatusBarLightMode(
        activity: Activity
    ) {
        val lightmode = ThemeManager.isLight()
        setMIUIStatusBarLightMode(activity, lightmode)
        setFlymeStatusBarLightMode(activity, lightmode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
            val view = activity.window.decorView
            var flag = view.systemUiVisibility
            flag = if (lightmode) {
                flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flag and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            view.systemUiVisibility = flag
        }
    }

    @Volatile
    private var sMiuiClazz: Class<*>? = null
    @Volatile
    private var sMiuiField1: Field? = null
    @Volatile
    private var sMiuiField2: Method? = null
    private var sMiuiError = false

    /**
     * MIUI 系统中设置 StatusBar 的文本颜色为黑色
     */
    private fun setMIUIStatusBarLightMode(
        activity: Activity,
        lightmode: Boolean
    ): Boolean {
        if (sMiuiError) return false
        var result = false
        try {
            var darkModeFlag = 0
            if (sMiuiClazz == null) {
                synchronized(StatusBarUtil::class.java) {
                    if (sMiuiClazz == null) {
                        sMiuiClazz = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                        sMiuiField1 = sMiuiClazz!!.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                        sMiuiField2 = activity.window.javaClass.getMethod(
                                "setExtraFlags",
                                Int::class.javaPrimitiveType,
                                Int::class.javaPrimitiveType
                            )
                    }
                }
            }
            darkModeFlag = sMiuiField1!!.getInt(sMiuiClazz)
            sMiuiField2!!.invoke(
                activity.window,
                if (lightmode) darkModeFlag else 0,
                darkModeFlag
            )
            result = true
        } catch (ignored: ClassNotFoundException) {
            sMiuiError = true
        } catch (ignored: NoSuchFieldException) {
            sMiuiError = true
        } catch (ignored: NoSuchMethodException) {
            sMiuiError = true
        } catch (ignored: Exception) {
        }
        return result
    }

    @Volatile
    private var sFlymeField1: Field? = null
    @Volatile
    private var sFlymeField2: Field? = null
    private var sFlymeError = false

    /**
     * Flyme 系统中设置 StatusBar 的文本颜色为黑色
     */
    private fun setFlymeStatusBarLightMode(
        activity: Activity,
        lightmode: Boolean
    ): Boolean {
        if (sFlymeError) return false
        var result = false
        try {
            if (sFlymeField1 == null) {
                synchronized(StatusBarUtil::class.java) {
                    if (sFlymeField1 == null) {
                        sFlymeField1 = WindowManager.LayoutParams::class.java
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                        sFlymeField2 = WindowManager.LayoutParams::class.java
                            .getDeclaredField("meizuFlags")
                        sFlymeField1!!.isAccessible = true
                        sFlymeField2!!.isAccessible = true
                    }
                }
            }
            val lp = activity.window.attributes
            val bit = sFlymeField1!!.getInt(null)
            var value = sFlymeField2!!.getInt(lp)
            value = if (lightmode) {
                value or bit
            } else {
                value and bit.inv()
            }
            sFlymeField2!!.setInt(lp, value)
            activity.window.attributes = lp
            result = true
        } catch (ignored: NoSuchFieldException) {
            sFlymeError = true
        } catch (ignored: java.lang.Exception) {
        }
        return result
    }


}

/**
 * FakeStatusBar Drawable
 */
class StatusBarDrawable(color: Int, height: Int) :
    Drawable() {
    private val paint: Paint = Paint()
    private val height: Int
    fun setColor(color: Int) {
        paint.color = color
    }

    override fun draw(@NonNull canvas: Canvas) {
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), height.toFloat(), paint)
    }

    override fun setAlpha(i: Int) {
        paint.alpha = i
    }

    override fun setColorFilter(@Nullable colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    init {
        paint.color = color
        this.height = height
    }
}

private class CombinedDrawable constructor(
    @param:Nullable val origin: Drawable?, @param:NonNull val statusBarDrawable: StatusBarDrawable
) : LayerDrawable(
        origin?.let { arrayOf(it, statusBarDrawable) } ?: arrayOf<Drawable>(statusBarDrawable)
    )