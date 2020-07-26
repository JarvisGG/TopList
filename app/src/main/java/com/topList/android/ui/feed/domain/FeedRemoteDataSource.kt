package com.topList.android.ui.feed.domain

import com.topList.android.api.NetResult
import com.topList.android.api.getResult
import com.topList.android.api.model.FeedItem
import com.topList.android.api.model.State
import com.topList.android.api.service.FeedService
import retrofit2.Response
import java.io.IOException

/**
 * @author yyf
 * @since 03-21-2020
 */
class FeedRemoteDataSource  (
    private val service: FeedService
) {
    suspend fun loadFeed(page: Int, isFollowed: Boolean): NetResult<State<List<FeedItem>>> {
        return try {
            val response = service.loadFeedList(page.toString(), if (isFollowed) "1" else "0")
            getResult(response = response, onError = {
                NetResult.Error(
                    IOException("Error getting feed ${response.code()} ${response.message()}")
                )
            })
        } catch (e: Exception) {
            NetResult.Error(IOException("Error getting feed", e))
        }
    }

    suspend fun collect(id: String) : NetResult<State<Any>> {
        return try {
            val response = service.collect(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return NetResult.Success(body)
                }
            }
            NetResult.Error(IOException("Error get subscribe data"))
        } catch (e: Exception) {
            NetResult.Error(IOException("Error get subscribe data", e))
        }
    }

}