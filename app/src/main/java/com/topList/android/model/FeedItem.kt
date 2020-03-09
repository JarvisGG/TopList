package com.topList.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 08-14-2019
 */
@Parcelize
@Serializable
data class FeedItem (
    @SerialName("title") val title: String,
    @SerialName("url") val url: String
): Parcelable