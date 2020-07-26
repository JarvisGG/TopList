package com.topList.android.ui.feed

import androidx.lifecycle.*
import com.topList.android.base.BaseViewModel
import com.topList.android.ui.LOADING
import com.topList.android.ui.feed.domain.LoadFeedParams
import com.topList.android.ui.feed.domain.LoadFeedUseCase

/**
 * @author yyf
 * @since 08-09-2019
 */

class FeedViewModel(
    private val loadFeedUseCase: LoadFeedUseCase
) : BaseViewModel() {

    private val _feedParams = MutableLiveData<LoadFeedParams>()

    private val _feedData = _feedParams.switchMap {
        liveData {
            emit(LOADING(true))
            emit(loadFeedUseCase(it))
            emit(LOADING(false))
        }
    }

    val feedData: LiveData<Any>
        get() = _feedData

    fun load(params: LoadFeedParams) {
        _feedParams.value = params
    }

    fun collect(id: String) = liveData {
        emit(loadFeedUseCase.collect(id))
    }
}

class FeedViewModelFactory(
    private val loadFeedUseCase: LoadFeedUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(loadFeedUseCase) as T
    }

}