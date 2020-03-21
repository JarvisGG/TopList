package com.topList.android.api.service

import com.topList.android.api.model.LoginResult
import com.topList.android.api.model.State
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

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
        @Field("userName") userName: String,
        @Field("email") email: String,
        @Field("code") code: String,
        @Field("pwd") pwd: String
    ): Response<State<LoginResult>>

    /**
     * 发送验证码 Email
     */
    @FormUrlEncoded
    @POST("UVerifyCode")
    fun sendEmailCode(
        @Query("email") email: String
    ): Response<State<Any>>

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