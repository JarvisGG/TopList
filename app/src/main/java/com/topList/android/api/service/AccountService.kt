package com.topList.android.api.service

import com.topList.android.api.model.LoginResult
import com.topList.android.api.model.State
import com.topList.android.api.model.VerifyResult
import retrofit2.Response
import retrofit2.http.*

/**
 * @author yyf
 * @since 03-21-2020
 */
interface AccountService {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("URegister")
    suspend fun register(
        @Field("userName") name: String,
        @Field("email") email: String,
        @Field("code") code: String,
        @Field("pwd") pwd: String
    ): Response<State<LoginResult>>

    /**
     * 发送验证码 Email
     */
    @GET("UVerifyCode")
    suspend fun sendEmailCode(
        @Query("email") email: String
    ): Response<VerifyResult>

    /**
     * 登陆
     */
    @FormUrlEncoded
    @POST("ULogin")
    suspend fun login(
        @Field("email") key: String, // 邮箱 或 用户名
        @Field("pwd") pwd: String
    ): Response<State<LoginResult>>


}