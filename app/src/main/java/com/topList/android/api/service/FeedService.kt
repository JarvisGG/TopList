package com.topList.android.api.service

import com.topList.android.api.model.FeedItem
import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author yyf
 * @since 08-12-2019
 */
interface FeedService {

//    /**
//     * 获得指定平台热榜
//     */
//    @GET("GetTypeInfo")
//    fun getFeedPage(
//        @Query("id") id: Int
//    ): Observable<Response<State<ArrayList<FeedItem>>>>
//
//    /**
//     * 获取所有分类
//     */
//    @GET("GetAllType")
//    fun getFeedTabs(): Observable<Response<State<ArrayList<FeedTab>>>>
//
//    @GET("GetAllType")
//    suspend fun getFeedTabsAsync(): Response<State<ArrayList<FeedTab>>>


    @GET("GetRandomInfo")
    suspend fun loadFeedList(
        @Query("time") page: String, // time 就是 page,从第 0 页开始计数
        @Query("is_follow") isFollow: String // 是否只获取订阅最新1是0否
    ): Response<State<FeedItem>>

    @GET("GetSiteType")
    suspend fun getSubscribeList(): Response<State<State<List<SubscribeItem>>>>

}