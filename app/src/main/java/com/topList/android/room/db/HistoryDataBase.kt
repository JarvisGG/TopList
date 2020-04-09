package com.topList.android.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.topList.android.room.dao.HistoryDao
import com.topList.android.room.model.HistoryEntity

/**
 * @author yyf
 * @since 04-09-2020
 */
@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun historyDataDao(): HistoryDao
}