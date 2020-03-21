package com.topList.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 03-21-2020
 */
/**
 * 我的用户信息
 */
@Parcelize
@Serializable
data class People(
    @SerialName("id") val id: String,
    @SerialName("admin") val admin: String,
    @SerialName("black") val black: String,
    @SerialName("blockId") val blockId: String,
    @SerialName("coin") val coin: String,
    @SerialName("collectionNum") val collectionNum: String,
    @SerialName("commentNum") val commentNum: String,
    @SerialName("experience") val experience: String,
    @SerialName("history") val history: String,
    @SerialName("img") val img: String,
    @SerialName("level") val level: String,
    @SerialName("levelName") val levelName: String,
    @SerialName("levelNext") val levelNext: String,
    @SerialName("levelNum") val levelNum: String,
    @SerialName("loginDays") val loginDays: String,
    @SerialName("loginFishBall") val loginFishBall: String,
    @SerialName("message_num") val messageNum: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("only_follow_list") val onlyFollowList: String,
    @SerialName("sex") val sex: String,
    @SerialName("signature") val signature: String
) : Parcelable