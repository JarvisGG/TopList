package com.topList.android.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

/**
 * @author yyf
 * @since 03-23-2020
 */
object EMPTY

class SplashViewModel : ViewModel() {

    val data = liveData {
        delay(1000)
        emit(EMPTY)
    }
}