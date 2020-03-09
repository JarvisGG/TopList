package com.topList.android.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import com.topList.android.api.Apis
import com.topList.android.base.BaseViewModel
import com.topList.android.model.FeedItem
import com.topList.android.utils.Resource

/**
 * @author yyf
 * @since 08-09-2019
 */
class FeedViewModel(application: Application) : BaseViewModel(application) {

    val feedItemsLiveData = MutableLiveData<Resource<ArrayList<FeedItem>>>()

    fun fetchFeedDataById(id: Int) {

        Apis.feed.getFeedPage(id)
            .subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({
                it.takeIf { res -> res.isSuccessful && res.body() != null }?.apply {
                    feedItemsLiveData.postValue(Resource.Success(it.body()!!.data))
                } ?: feedItemsLiveData.postValue(Resource.Error(Throwable(it.message())))

            }) {
                feedItemsLiveData.postValue(Resource.Error(it))
            }
    }
}