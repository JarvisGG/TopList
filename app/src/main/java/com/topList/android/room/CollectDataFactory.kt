package com.topList.android.room

import androidx.room.migration.Migration
import com.topList.android.room.db.CollectDataBase
import com.topList.android.room.db.HistoryDataBase

/**
 * @author yyf
 * @since 07-03-2019
 */
class CollectDataFactory: RoomFactory<CollectDataBase>() {

    override fun addMigrations(): Array<Migration>? = emptyArray()

    public override fun roomDbName() = "history_data.room"

    override fun deleteRoomIfMigrationNeeded() = false

    companion object {
        @Volatile private var INSTANCE: CollectDataFactory? = null

        fun getInstance(): CollectDataFactory =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: CollectDataFactory().also { INSTANCE = it }
                }
    }

}