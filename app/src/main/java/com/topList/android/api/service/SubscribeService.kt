package com.topList.android.api.service

import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem
import retrofit2.Response
import retrofit2.http.GET

interface SubscribeService {

    @GET("GetSiteType")
    suspend fun getSubscribeList(): Response<State<List<SubscribeItem>>>
}