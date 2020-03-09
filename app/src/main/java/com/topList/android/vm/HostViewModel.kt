package com.topList.android.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.schedulers.Schedulers
import com.topList.android.api.Apis
import com.topList.android.base.BaseViewModel
import com.topList.android.model.FeedTab
import com.topList.android.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author yyf
 * @since 08-09-2019
 */
class HostViewModel(application: Application) : BaseViewModel(application) {

    val feedTabsLiveData = MutableLiveData<Resource<ArrayList<FeedTab>>>()

    fun fetchFeedTabs() {
        Apis.feed.getFeedTabs()
            .subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({
                it.takeIf { res -> res.isSuccessful && res.body() != null }?.apply {
                    feedTabsLiveData.postValue(Resource.Success(it.body()!!.data))
                } ?: feedTabsLiveData.postValue(Resource.Error(Throwable(it.message())))

            }) {
                feedTabsLiveData.postValue(Resource.Error(it))
            }
    }

    fun fetchFeedTabsAsync() {
        viewModelScope.launch(Dispatchers.IO) {
//            try {
                val response = Apis.feed.getFeedTabsAsync()
                if (response.isSuccessful) {
                    feedTabsLiveData.postValue(Resource.Success(response.body()!!.data))
                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        }

    }

    val drawerSelectedTab = MutableLiveData<FeedTab>()

}