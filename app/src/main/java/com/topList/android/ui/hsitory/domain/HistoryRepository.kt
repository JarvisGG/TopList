package com.topList.android.ui.hsitory.domain

/**
 * @author yyf
 * @since 04-09-2020
 */
class HistoryRepository (
    private val dataSource: HistoryLocalDataSource
) {
    suspend fun loadHistory() = dataSource.loadHistory()
}