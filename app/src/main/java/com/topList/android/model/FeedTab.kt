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
data class FeedTab(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("icon") val icon: String = "",
    @SerialName("img") val img: String = ""
): Parcelable