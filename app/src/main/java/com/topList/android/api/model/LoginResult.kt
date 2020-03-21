package com.topList.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 03-21-2020
 */
@Parcelize
@Serializable
data class LoginResult(
    @SerialName("img") val avatar: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("token") val token: String
) : Parcelable