package com.topList.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 03-28-2020
 */
@Parcelize
@Serializable
data class SearchItem(
    @SerialName("CreateTime") val createTime: String,
    @SerialName("Desc") val desc: String,
    @SerialName("Title") val title: String,
    @SerialName("Url") val icon: String,
    @SerialName("hotDesc") val img: String,
    @SerialName("imgUrl") val url: String,
    @SerialName("id") val id: String,
    @SerialName("tid") val tid: String,
    @SerialName("type") val type: String
) : Parcelable
