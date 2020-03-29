package com.topList.android.ui.search

import androidx.lifecycle.*
import com.topList.android.ui.LOADING
import com.topList.android.ui.search.domain.SearchUseCase

/**
 * @author yyf
 * @since 03-28-2020
 */
class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableLiveData<String>()

    private val _searchResult= _searchQuery.switchMap {
        liveData {
            emit(LOADING(true))
            emit(searchUseCase(it))
            emit(LOADING(false))
        }
    }

    val searchResult: LiveData<Any>
        get() = _searchResult

    fun search(key: String) {
        _searchQuery.value = key
    }
}

class SearchModelFactory(
    private val searchUseCase: SearchUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchUseCase) as T
    }

}