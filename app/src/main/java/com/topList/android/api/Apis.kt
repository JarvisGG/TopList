package com.topList.android.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.topList.android.BuildConfig
import com.topList.android.api.service.FeedService
import com.topList.android.api.service.SearchService
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @author yyf
 * @since 08-12-2019
 */
internal object Apis {

    val MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    val json: StringFormat = Json(JsonConfiguration.Stable)
    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.tophub.fun:8888/")
            .client(OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }).build()
            ).addConverterFactory(json.asConverterFactory(MEDIA_TYPE))
            .build()

    /**
     * 所有 Http 接口
     */
    val feed: FeedService = retrofit.create(FeedService::class.java)

    /**
     * 搜索接口
     */
    val search: SearchService = retrofit.create(SearchService::class.java)
}