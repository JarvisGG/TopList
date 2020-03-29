package com.topList.android.ui.subscribe

import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem
import com.topList.android.ui.subscribe.model.*
import timber.log.Timber

object SubscribeHelper {
    fun getCategoryData(result: NetResult<State<List<SubscribeItem>>>): SubscribeUseItem {
        val categoryList = mutableListOf<CategoryItem>()
        val stationList = mutableListOf<Station>()
        val sourceList = mutableListOf<SubscribeItem>()
        when (result) {
            is NetResult.Success -> {
                sourceList.addAll(result.data.data)
                for (category in result.data.data) {
                    categoryList.add(CategoryItem(category.name, false))
                    stationList.add(StationHeaderUseItem(category.name))
                    for (station in category.list) {
                        stationList.add(StationUseItem(station, category.name))
                    }
                }
                if (categoryList.size > 0)
                    categoryList[0].check = true
            }
            is NetResult.Error -> {

            }
        }
        return SubscribeUseItem(categoryList, stationList, sourceList)
    }
}