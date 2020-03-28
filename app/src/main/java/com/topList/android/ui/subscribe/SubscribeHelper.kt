package com.topList.android.ui.subscribe

import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem
import com.topList.android.ui.subscribe.model.CategoryItem
import com.topList.android.ui.subscribe.model.StationUseItem
import com.topList.android.ui.subscribe.model.SubscribeUseItem

object SubscribeHelper {
    fun getCategoryData(result: NetResult<State<State<List<SubscribeItem>>>>): SubscribeUseItem {
        val categoryList = mutableListOf<CategoryItem>()
        val stationList = mutableListOf<StationUseItem>()
        when (result) {
            is NetResult.Success -> {
                for (category in result.data.data.data) {
                    categoryList.add(CategoryItem(category.name, false))
                    for (station in category.list) {
                        stationList.add(StationUseItem(station))
                    }
                }
                if (categoryList.size > 0)
                    categoryList[0].check = true
            }
            is NetResult.Error -> {

            }
        }
        return SubscribeUseItem(categoryList, stationList)
    }
}