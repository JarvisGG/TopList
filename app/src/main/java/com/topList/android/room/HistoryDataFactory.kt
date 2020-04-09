package com.topList.android.room

import androidx.room.migration.Migration
import com.topList.android.room.db.HistoryDataBase

/**
 * @author yyf
 * @since 07-03-2019
 */
class HistoryDataFactory: RoomFactory<HistoryDataBase>() {

    override fun addMigrations(): Array<Migration>? = emptyArray()

    public override fun roomDbName() = "history_data.room"

    override fun deleteRoomIfMigrationNeeded() = false

    companion object {
        @Volatile private var INSTANCE: HistoryDataFactory? = null

        fun getInstance(): HistoryDataFactory =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: HistoryDataFactory().also { INSTANCE = it }
                }
    }

}