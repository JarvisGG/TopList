package com.topList.android.api.service

import com.topList.android.api.model.SearchItem
import com.topList.android.api.model.State
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author yyf
 * @since 03-28-2020
 */
interface SearchService {

    @GET("SearchKey")
    suspend fun loadSearchList(
        @Query("key") key: String // 搜索关键字
    ): Response<State<List<SearchItem>>>

}