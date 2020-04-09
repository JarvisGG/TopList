package com.topList.android.room

import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.annotation.WorkerThread
import com.topList.android.room.dao.HistoryDao
import org.jetbrains.annotations.Nullable

/**
 * @author yyf
 * @since 04-09-2020
 */
object HistoryRoomHelper {

    @WorkerThread
    fun openHistoryDatabaseForDao(@Nullable context: Context): HistoryDao? {
        return try {
            HistoryDataFactory.getInstance().getDataBase(context).let {
                it.openHelper.writableDatabase
                it.historyDataDao()
            }
        } catch (e: SQLiteException) {
            closePanelDataDatabase()
            context.deleteDatabase(HistoryDataFactory.getInstance().roomDbName())
            null
        }
    }

    @WorkerThread
    fun closePanelDataDatabase() {
        try {
            HistoryDataFactory.getInstance().close()
        } catch (e: Exception) {
        }
    }
}