package com.topList.android.ui.hsitory

import androidx.lifecycle.*
import com.topList.android.ui.LOADING
import com.topList.android.ui.hsitory.domain.HistoryUseCase

/**
 * @author yyf
 * @since 04-09-2020
 */
class HistoryViewModel(
    private val loadHistoryUseCase: HistoryUseCase
): ViewModel() {

    private val _historyResult =
        liveData {
            emit(LOADING(true))
            emit(loadHistoryUseCase())
            emit(LOADING(false))
        }

    val historyResult: LiveData<Any?>
        get() = _historyResult


}

class HistoryViewModelFactory(
    private val loadHistoryUseCase: HistoryUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(loadHistoryUseCase) as T
    }



}