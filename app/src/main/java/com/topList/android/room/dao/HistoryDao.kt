package com.topList.android.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topList.android.room.model.HistoryEntity

/**
 * @author yyf
 * @since 04-09-2020
 */
@Dao
interface HistoryDao {

    @Query("SELECT * FROM history_entity")
    suspend fun getData(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: HistoryEntity)

    @Query("DELETE FROM history_entity")
    suspend fun deleteAll()
}