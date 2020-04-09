package com.topList.android.ui.hsitory.domain

/**
 * @author yyf
 * @since 04-09-2020
 */
class HistoryUseCase (
    private val repository: HistoryRepository
) {
    suspend operator fun invoke() = repository.loadHistory()?.reversed()
}