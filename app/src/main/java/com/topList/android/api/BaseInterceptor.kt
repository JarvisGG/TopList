package com.topList.android.api

import com.topList.android.App
import okhttp3.Interceptor
import okhttp3.Response


/**
 * @author yyf
 * @since 07-09-2020
 * @desc
 *  1.添加平台
 *  2.添加 Token 验证
 */
class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldReq = chain.request()
        val newReqBuilder = oldReq.newBuilder()
        newReqBuilder.run {
            addHeader("platform", "android")
            addHeader("Authorization", App.INSTANCE?.accountManager?.getToken()?.token ?: "")
        }
        return chain.proceed(newReqBuilder.build())
    }

}