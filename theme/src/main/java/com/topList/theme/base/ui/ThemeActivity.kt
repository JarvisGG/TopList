package com.topList.theme.base.ui

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.AppCompatDrawableManager
import com.topList.theme.ThemeManager
import com.topList.theme.base.ThemedInflater
import com.topList.theme.base.iface.ThemeSwitchable
import com.topList.theme.base.utils.NavigationBarUtil
import com.topList.theme.base.utils.ResourceFlusher
import com.topList.theme.base.utils.StatusBarUtil
import com.topList.theme.bitmap

/**
 * @author yyf
 * @since 03-10-2020
 */
open class ThemeActivity : AppCompatActivity(), ThemeSwitchable {

    private val MODE_NIGHT_UNSPECIFIED = -100

    var isActive = false

    @AppCompatDelegate.NightMode
    private var mLocalNightMode = MODE_NIGHT_UNSPECIFIED

    private var originThemeId = -1

    /**
     * 是否要作用全局
     */
    var overrideDefaultDayNightMode = false

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemedInflater.installViewFactory(this)
        StatusBarUtil.translucentStatusBar(this)
        super.onCreate(savedInstanceState)
        recolorBackground()
    }

    override fun onResume() {
        super.onResume()
        isActive = true
        ThemeManager.updateConfigurationIfNeeded(this)
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected open fun onPrepareThemeChanged(toMode: Int, anim: Boolean = false) {
        updateSystemUiColor(anim)
    }

    protected open fun onPostThemeChanged(toMode: Int) {
    }

    protected open fun updateSystemUiColor(anim: Boolean) {
        StatusBarUtil.setStatusBarLightMode(this)
        NavigationBarUtil.setDefaultNavigationBarColor(this, anim)
    }

    @AppCompatDelegate.NightMode
    open fun getNightMode(): Int {
        val mode: Int = if (overrideDefaultDayNightMode) {
            if (mLocalNightMode != MODE_NIGHT_UNSPECIFIED) mLocalNightMode else AppCompatDelegate.getDefaultNightMode()
        } else {
            AppCompatDelegate.getDefaultNightMode()
        }
        return getMode(mode)
    }

    override fun switchTheme(@AppCompatDelegate.NightMode mode: Int) {
        if (overrideDefaultDayNightMode) {
            return
        }
        val toMode = getMode(mode)
        if (isActive) {
            val decorView = window.decorView
            val drawingCache = decorView.bitmap
            if (decorView is ViewGroup && drawingCache != null) {
                switchWithAnimation(toMode, decorView, drawingCache)
                return
            }
        }
        switchWithoutAnimation(toMode)
    }

    private fun switchWithAnimation(
        toMode: Int,
        decorView: View,
        drawingCache: Bitmap
    ) {
        val maskView = View(this)
        maskView.background = BitmapDrawable(this.resources, drawingCache)
        (decorView as ViewGroup).addView(maskView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        switchThemeInternal(toMode)
        onPrepareThemeChanged(toMode, anim = true)
        maskView.animate().alpha(0f).setDuration(300)
            .setListener(object : AnimationListener() {
                override fun onAnimationEnd(animation: Animator) {
                    decorView.removeView(maskView)
                    onPostThemeChanged(toMode)
                }
            }).start()
    }

    private fun switchWithoutAnimation(toMode: Int) {
        switchThemeInternal(toMode)
        onPrepareThemeChanged(toMode, anim = false)
        onPostThemeChanged(toMode)
    }

    open fun setLocalDayNightMode(@AppCompatDelegate.NightMode mode: Int) {
        val reMode = getMode(mode)
        delegate.localNightMode = reMode
        overrideDefaultDayNightMode = true
        mLocalNightMode = reMode
    }

    /**
     * 设置日夜模式，影响全局，不会遍历 View 修改样式
     */
    open fun setDayNightMode(@AppCompatDelegate.NightMode mode: Int) {
        val reMode = getMode(mode)
        AppCompatDelegate.setDefaultNightMode(reMode)
        delegate.localNightMode = reMode
        mLocalNightMode = reMode
    }

    /**
     * 遍历 View 修改样式
     */
    private fun switchThemeInternal(@AppCompatDelegate.NightMode mode: Int) {
        ResourceFlusher.flush(resources)
        setDayNightMode(mode)
        invalidateOptionsMenu()
        delegate.invalidateOptionsMenu()
        clearDrawableCache()
        reTheme()
        recolorBackground()
        ThemeManager.switchViewTree(window.decorView)
    }

    override fun switchSilently(@AppCompatDelegate.NightMode mode: Int) {
        ResourceFlusher.flush(resources)
        setDayNightMode(mode)
        clearDrawableCache()
        reTheme()
    }

    private fun clearDrawableCache() {
        try { //清除缓存
            AppCompatDrawableManager.get().onConfigurationChanged(this)
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
    }

    override fun setTheme(@StyleRes resid: Int) {
        super.setTheme(resid)
        originThemeId = resid
    }

    private fun reTheme() {
        super.setTheme(0)
        super.setTheme(originThemeId)
    }

    protected open fun recolorBackground() {
        val array = obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
        val colorDrawable = ColorDrawable(array.getColor(0, 0))
        array.recycle()
        window.setBackgroundDrawable(colorDrawable)
    }

    @AppCompatDelegate.NightMode
    private fun getMode(@AppCompatDelegate.NightMode mode: Int): Int {
        return if (mode != MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES
    }

    private open class AnimationListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    }

}