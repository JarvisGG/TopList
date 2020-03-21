package com.topList.android.base.model

import okhttp3.ResponseBody

/**
 * @author yyf
 * @since 03-21-2020
 */

data class Error(val code: Int, val msg: String)

class ApiException internal constructor(override var message: String) : Throwable()

object ApiError {
    fun from(errorBody: ResponseBody?): Error {
        if (errorBody != null) {
            return getDefault()
        } else {
            return getDefault()
        }
    }

    fun getDefault() = Error(0, "something wrong.")
}