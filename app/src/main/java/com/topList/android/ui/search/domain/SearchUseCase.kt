package com.topList.android.ui.search.domain

import com.topList.android.api.NetResult
import com.topList.android.api.model.SearchItem
import com.topList.android.api.model.State

/**
 * @author yyf
 * @since 03-28-2020
 */
class SearchUseCase (
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(key: String): NetResult<State<SearchItem>> {
        val result = searchRepository.loadSearch(key)
        return when (result) {
            is NetResult.Success -> {
                // transform data
                NetResult.Success(result.data)
            }
            is NetResult.Error -> {
                result
            }
        }
    }
}