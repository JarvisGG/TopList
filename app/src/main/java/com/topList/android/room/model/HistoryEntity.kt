package com.topList.android.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.topList.android.ui.hsitory.HistoryModel
import java.util.*

/**
 * @author yyf
 * @since 04-09-2020
 */
@Entity(tableName = "history_entity")
class HistoryEntity {

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "history_model")
    @TypeConverters(HistoryConverter::class)
    var data: HistoryModel = HistoryModel()

}