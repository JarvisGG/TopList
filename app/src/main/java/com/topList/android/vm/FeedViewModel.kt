package com.topList.android.vm

import com.topList.android.base.BaseViewModel

/**
 * @author yyf
 * @since 08-09-2019
 */
class FeedViewModel : BaseViewModel() {



//    val feedItemsLiveData = MutableLiveData<Resource<ArrayList<FeedItem>>>()
//
//    fun fetchFeedDataById(id: Int) {
//
//        Apis.feed.getFeedPage(id)
//            .subscribeOn(Schedulers.io())
//            .autoDisposable()
//            .subscribe({
//                it.takeIf { res -> res.isSuccessful && res.body() != null }?.apply {
//                    feedItemsLiveData.postValue(Resource.Success(it.body()!!.data))
//                } ?: feedItemsLiveData.postValue(Resource.Error(Throwable(it.message())))
//
//            }) {
//                feedItemsLiveData.postValue(Resource.Error(it))
//            }
//    }


}