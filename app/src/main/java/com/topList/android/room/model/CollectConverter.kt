package com.topList.android.room.model

import androidx.room.TypeConverter
import com.topList.android.ui.feed.model.CollectModel
import com.topList.android.ui.hsitory.HistoryModel
import kotlinx.serialization.json.Json

/**
 * @author yyf
 * @since 04-09-2020
 */
class CollectConverter {
    @TypeConverter
    fun dataToString(data: CollectModel) = Json.stringify(CollectModel.serializer(), data)

    @TypeConverter
    fun stringToData(dataStr: String) = Json.parse(CollectModel.serializer(), dataStr)
}