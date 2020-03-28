package com.topList.android.ui.subscribe.domain

import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.api.model.SubscribeItem

class SubscribeUseCase (
    private val subscribeRepository: SubscribeRepository
) {
    suspend operator fun invoke(): NetResult<State<State<List<SubscribeItem>>>> {
        return when(val result = subscribeRepository.loadSubscribeInfo()) {
            is NetResult.Success -> {
                NetResult.Success(result.data)
            }
            is NetResult.Error -> {
                result
            }
        }
    }
}