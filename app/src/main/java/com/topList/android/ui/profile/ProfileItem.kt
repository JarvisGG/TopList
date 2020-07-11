package com.topList.android.ui.profile

import com.topList.android.R
import com.topList.android.ui.account.AccountManager
import com.topList.android.ui.profile.holder.ItemData
import com.topList.android.ui.profile.holder.LabelData

/**
 * @author yyf
 * @since 03-12-2020
 */
enum class ProfileItem(
    val iconRes: Int,
    val title: String,
    val isSwitch: Boolean = false
) {
    HISTORY(R.drawable.ic_history, "历史记录"),
    LOGOUT(R.drawable.ic_logout, "退出登陆"),
    THEME(R.drawable.ic_theme, "主题", true),
    FRONT(R.drawable.ic_font, "字体大小"),
    FEEDBACK(R.drawable.ic_feedback, "用户反馈")
}

val sProfileList = arrayListOf(
    LabelData("阅读"),
    ItemData(ProfileItem.HISTORY),
    LabelData("设置"),
    ItemData(ProfileItem.THEME),
    ItemData(ProfileItem.FRONT),
    LabelData("反馈"),
    ItemData(ProfileItem.FEEDBACK)
)

val sLoginList = arrayListOf(
    LabelData("阅读"),
    ItemData(ProfileItem.HISTORY),
    LabelData("设置"),
    ItemData(ProfileItem.LOGOUT),
    ItemData(ProfileItem.THEME),
    ItemData(ProfileItem.FRONT),
    LabelData("反馈"),
    ItemData(ProfileItem.FEEDBACK)
)