package com.topList.android.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.topList.android.accountManager
import kotlinx.coroutines.delay

/**
 * @author yyf
 * @since 03-23-2020
 */
class SplashViewModel(
    application: Application
) : AndroidViewModel(application) {

    val data = liveData {
        delay(1000)
        emit(application.accountManager.restore())
    }
}