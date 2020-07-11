package com.topList.android.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.topList.android.api.model.People

/**
 * @author yyf
 * @since 07-09-2020
 */
class CommonViewModel : ViewModel() {
    val accountState = MutableLiveData<People>()
}