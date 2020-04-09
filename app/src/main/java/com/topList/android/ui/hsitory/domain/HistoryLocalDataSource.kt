package com.topList.android.ui.hsitory.domain

import com.topList.android.room.dao.HistoryDao

/**
 * @author yyf
 * @since 04-09-2020
 */
class HistoryLocalDataSource(
    private val dao: HistoryDao?
) {
    suspend fun loadHistory() = dao?.getData()
}