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
data class FeedItem(
    @SerialName("CreateTime") val createTime: String,
    @SerialName("Desc") val desc: String,
    @SerialName("Title") val title: String,
    @SerialName("icon") val icon: String,
    @SerialName("img") val img: String,
    @SerialName("Url") val url: String,
    @SerialName("id") val id: String,
    @SerialName("tid") val tid: String,
    @SerialName("type") val type: String
) : Parcelable