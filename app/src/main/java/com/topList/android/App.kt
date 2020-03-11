package com.topList.android

import android.app.Application
import com.topList.theme.ThemeManager

/**
 * @author yyf
 * @since 08-12-2019
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ThemeManager.init(this)
    }
}