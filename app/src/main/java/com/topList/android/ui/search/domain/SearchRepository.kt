package com.topList.android.ui.search.domain

import com.topList.android.api.NetResult
import com.topList.android.api.getResult
import com.topList.android.api.model.SearchItem
import com.topList.android.api.model.State
import com.topList.android.api.service.SearchService
import java.io.IOException

/**
 * @author yyf
 * @since 03-28-2020
 */
class SearchRepository (
    private val service: SearchService
) {

    suspend fun loadSearch(key: String): NetResult<State<SearchItem>> {
        return try {
            val response = service.loadSearchList(key)
            getResult(response = response, onError = {
                NetResult.Error(
                    IOException("Error getting feed ${response.code()} ${response.message()}")
                )
            })
        } catch (e: Exception) {
            NetResult.Error(IOException("Error getting feed", e))
        }
    }
}