package com.topList.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SubscribeItem(
    @SerialName("name") val name: String,
    @SerialName("list") val list: List<StationItem>
) : Parcelable

@Parcelize
@Serializable
data class StationItem(
    @SerialName("follow_number") val followNumber: Int,
    @SerialName("hot") val hot: String,
    @SerialName("site_desc") val siteDesc: String,
    @SerialName("type") val type: String,
    @SerialName("name") val name: String,
    @SerialName("img") val img: String,
    @SerialName("id") val id: String,
    @SerialName("icon") val icon: String,
    @SerialName("update_time") val updateTime: String
) : Parcelable