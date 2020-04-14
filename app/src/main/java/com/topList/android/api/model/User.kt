package com.topList.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 04-13-2020
 */

@Parcelize
@Serializable
data class User(
    @SerialName("avatar") val avatar: String,
    @SerialName("name") val name: String
) : Parcelable