package com.topList.android.utils

/**
 * https://developer.android.com/jetpack/docs/guide#addendum
 */
sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()

    class Loading<T>(val data: T? = null) : Resource<T>()

    class Error<T>(
        val throwable: Throwable? = null,
        val message: String? = null,
        val data: T? = null
    ) : Resource<T>() {
        fun getMeaningMessage() = message ?: throwable?.message ?: ""
    }
}