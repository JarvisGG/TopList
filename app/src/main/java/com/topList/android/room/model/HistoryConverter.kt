package com.topList.android.room.model

import androidx.room.TypeConverter
import com.topList.android.ui.hsitory.HistoryModel
import kotlinx.serialization.json.Json

/**
 * @author yyf
 * @since 04-09-2020
 */
class HistoryConverter {
    @TypeConverter
    fun dataToString(data: HistoryModel) = Json.stringify(HistoryModel.serializer(), data)

    @TypeConverter
    fun stringToData(dataStr: String) = Json.parse(HistoryModel.serializer(), dataStr)
}