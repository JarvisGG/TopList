package com.topList.android.ui.feed.domain

import com.topList.android.api.NetResult
import com.topList.android.api.model.FeedItem
import com.topList.android.api.model.State

/**
 * @author yyf
 * @since 03-21-2020
 */
data class LoadFeedParams(val page: Int, val isFollowed: Boolean)


class LoadFeedUseCase (
    private val feedRepository: FeedRepository
) {
    suspend operator fun invoke(params: LoadFeedParams): NetResult<State<List<FeedItem>>> {
        val result = feedRepository.loadFeed(params.page, params.isFollowed)
        return when (result) {
            is NetResult.Success -> {
                // transform data
                NetResult.Success(result.data)
            }
            is NetResult.Error -> {
                result
            }
        }
    }
}