package com.topList.android.ui.feed.domain

import com.topList.android.api.NetResult
import com.topList.android.api.model.FeedItem
import com.topList.android.api.model.State

/**
 * @author yyf
 * @since 03-21-2020
 */
class FeedRepository (
    private val remoteDataSource: FeedRemoteDataSource
) {

    suspend fun loadFeed(page: Int, isFollowed: Boolean) = getData { remoteDataSource.loadFeed(page, isFollowed) }


    private suspend fun getData(
        request: suspend () -> NetResult<State<List<FeedItem>>>
    ): NetResult<State<List<FeedItem>>> {
        val result = request()
        if (result is NetResult.Success) {
            cache(result.data)
        }
        return result
    }

    private fun cache(data: State<List<FeedItem>>) {

    }

}