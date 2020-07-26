package com.topList.android.ui.subscribe.domain

import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem

class SubscribeRepository(
    private val dataSource: SubscribeRemoteDataSource
) {
    suspend fun loadSubscribeInfo() = getData {
        dataSource.loadSubscribeItems()
    }

    suspend fun subscribe(id: String) = dataSource.subscribe(id)

    private suspend fun getData(
        request: suspend () -> NetResult<State<List<SubscribeItem>>>
    ): NetResult<State<List<SubscribeItem>>> {
        val result = request()
        if (result is NetResult.Success) {

        }
        return result
    }
}