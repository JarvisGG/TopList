package com.topList.android.api.service

import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscribeService {

    @GET("GetSiteType")
    suspend fun getSubscribeList(): Response<State<List<SubscribeItem>>>

    @GET("AddFollowData")
    suspend fun subscribe(
        @Query("id") id: String
    ): Response<State<Any>>
}