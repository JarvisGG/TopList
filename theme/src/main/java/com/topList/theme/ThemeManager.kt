package com.topList.theme

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatDelegate
import com.topList.theme.base.event.ThemeEvent
import com.topList.theme.base.iface.IDayNightView
import com.topList.theme.base.iface.ThemeSwitchable
import com.topList.theme.base.utils.SimpleActivityLifecycleCallbacks
import java.util.*

/**
 * @author yyf
 * @since 03-09-2020
 */
object ThemeManager {

    private var applicationContext: Application? = null
    private var sLogger: ThemeLogger? = null

    /**
     * 所有要切换 Theme 的 Activity
     */
    private val sSwitchables = ArrayList<ThemeSwitchable>()

    const val LIGHT = AppCompatDelegate.MODE_NIGHT_NO

    const val DARK = AppCompatDelegate.MODE_NIGHT_YES

    private var sCurrentMode = LIGHT

    private var listener: ((theme: ThemeEvent) -> Unit)? = null

    fun init(context: Context, listener: (theme: ThemeEvent) -> Unit) {
        applicationContext = context.applicationContext as Application
        val theme = readTheme(context)
        if (theme == DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        applicationContext!!.registerActivityLifecycleCallbacks(object :
            SimpleActivityLifecycleCallbacks() {
            override fun onActivityCreated(
                activity: Activity?,
                savedInstanceState: Bundle?
            ) {
                if (activity is ThemeSwitchable) {
                    sSwitchables.add(activity as ThemeSwitchable)
                }
            }

            override fun onActivityDestroyed(activity: Activity?) {
                if (activity is ThemeSwitchable) {
                    sSwitchables.remove(activity)
                }
            }
        })
        this.listener = listener
    }

    fun switchViewTree(view: View?) {
        if (view is IDayNightView) {
            try {
                (view as IDayNightView).resetStyle()
            } catch (e: Exception) {
                logException(e)
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val subView = view.getChildAt(i)
                switchViewTree(subView)
            }
        }
    }

    fun switchThemeTo(@Theme mode: Int) {
        listener?.invoke(ThemeEvent(mode))
        setCurrentTheme(mode)
        for (activity in sSwitchables) {
            activity.switchTheme(mode)
        }
    }

    fun isLight(): Boolean {
        return sCurrentMode != DARK
    }

    fun isDark(): Boolean {
        return sCurrentMode == DARK
    }

    fun getCurrentTheme(): Int {
        return sCurrentMode
    }

    @Theme
    fun readTheme(pContext: Context): Int {
        val sharedPreferences =
            pContext.getSharedPreferences("theme", Context.MODE_PRIVATE)
        var theme = sharedPreferences.getInt("theme", LIGHT)
        if (theme != LIGHT && theme != DARK) {
            theme = LIGHT
        }
        //每当从 SharedPreferences 读取了值，都重新赋给 sCurrentMode，加了这一行，莫名的感到安心
        sCurrentMode = theme
        return theme
    }

    fun setCurrentTheme(@Theme pTheme: Int) {
        val sharedPreferences =
            applicationContext!!.getSharedPreferences("theme", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("theme", pTheme).apply()
        //每当从 SharedPreferences 修改了值，都重新赋给 sCurrentMode；
        sCurrentMode = pTheme
    }

    fun logException(e: Throwable) {
        e.printStackTrace()
        if (sLogger != null) {
            sLogger!!.log(e)
        }
    }

    fun setLogger(logger: ThemeLogger?) {
        sLogger = logger
    }

    fun updateConfigurationIfNeeded(context: Context): Boolean {
        val res = context.resources
        val conf = res.configuration
        val currentNightMode = conf.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newNightMode = if (sCurrentMode == DARK) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
        if (currentNightMode != newNightMode) {
            val config = Configuration(conf)
            val metrics = res.displayMetrics
            config.uiMode = newNightMode or (config.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv())
            res.updateConfiguration(config, metrics)
            val switchable = findSwitchable(context)
            switchable?.switchSilently(sCurrentMode)
            return true
        }
        return false
    }

    private fun findSwitchable(context: Context): ThemeSwitchable? {
        var context: Context? = context
        while (context !is ThemeSwitchable) {
            context = if (context is ContextWrapper) {
                context.baseContext
            } else {
                return null
            }
        }
        return context
    }

    @IntDef(LIGHT, DARK)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Theme

    interface ThemeLogger {
        fun log(throwable: Throwable?)
    }


}