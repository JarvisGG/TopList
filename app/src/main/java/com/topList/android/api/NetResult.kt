/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.topList.android.api

import com.topList.android.api.model.FeedItem
import com.topList.android.api.model.State
import retrofit2.Response

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : NetResult<T>()
    data class Error(val exception: Exception) : NetResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}


inline fun <T> getResult(
    response: Response<State<T>>,
    crossinline onSuccess: (State<T>) -> Unit = {},
    crossinline onError: () -> NetResult.Error
): NetResult<State<T>> {
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            return NetResult.Success(body)
        }
    }
    return onError.invoke()
}