package com.topList.android.ui.subscribe.model

import com.topList.android.api.model.SubscribeItem

data class SubscribeUseItem(
    val categoryList: List<CategoryItem>,
    val stationList: List<Station>,
    val sourceList: List<SubscribeItem>
)