package com.topList.android.ui.subscribe.domain

import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem
import com.topList.android.api.service.FeedService
import retrofit2.Response
import java.io.IOException

class SubscribeRemoteDataSource(
    private val service: FeedService
) {
    suspend fun loadSubscribeItems(): NetResult<State<State<List<SubscribeItem>>>> {
        return try {
            val response = service.getSubscribeList()
            getResult(response) {
                NetResult.Error(IOException("Error get subscribe data ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            NetResult.Error(IOException("Error get subscribe data", e))
        }
    }

    private inline fun getResult(
        response: Response<State<State<List<SubscribeItem>>>>,
        onError: () -> NetResult.Error
    ): NetResult<State<State<List<SubscribeItem>>>> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return NetResult.Success(body)
            }
        }
        return onError.invoke()
    }
}