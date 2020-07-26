package com.topList.android.room

import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.annotation.WorkerThread
import com.topList.android.room.dao.CollectDao
import com.topList.android.room.dao.HistoryDao
import org.jetbrains.annotations.Nullable

/**
 * @author yyf
 * @since 04-09-2020
 */
object CollectRoomHelper {

    @WorkerThread
    fun openHistoryDatabaseForDao(@Nullable context: Context): CollectDao? {
        return try {
            CollectDataFactory.getInstance().getDataBase(context).let {
                it.openHelper.writableDatabase
                it.collectDataDao()
            }
        } catch (e: SQLiteException) {
            closePanelDataDatabase()
            context.deleteDatabase(CollectDataFactory.getInstance().roomDbName())
            null
        }
    }

    @WorkerThread
    fun closePanelDataDatabase() {
        try {
            CollectDataFactory.getInstance().close()
        } catch (e: Exception) {
        }
    }
}