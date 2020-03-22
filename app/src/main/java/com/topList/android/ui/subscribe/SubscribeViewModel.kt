package com.topList.android.ui.subscribe

import androidx.lifecycle.viewModelScope
import com.topList.android.api.Apis
import com.topList.android.base.BaseViewModel
import com.topList.android.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class SubscribeViewModel : BaseViewModel() {

    init {
        fetchSubscribeInfo()
    }

    private fun fetchSubscribeInfo() {
        viewModelScope.launch {
            try {
                val response = Apis.feed.getSubscribeList()
                if (response.isSuccessful) {
                    Timber.e("${Resource.Success(response.body()!!.data)}")
                } else {
                    Timber.e("${response.errorBody()}")
                }
            } catch (e: Exception) {
                Timber.e(e)
            }

        }
    }
}