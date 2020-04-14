package com.topList.android.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.topList.android.api.model.Token
import com.topList.android.api.model.User

/**
 * @author yyf
 * @since 03-19-2020
 */
class Files(
    context: Context
) : FileManager(ContextCompat.getDataDir(context)!!) {

    companion object {
        private const val DIR_ROOT = "data/"
        private const val DIR_ACCOUNT = DIR_ROOT + "account/"

        const val TOKEN = DIR_ACCOUNT + "token.json"
        const val USER = DIR_ACCOUNT + "user.json"
    }

    suspend fun saveToken(token: Token?) = save(TOKEN, token)
    suspend fun loadToken() = load<Token>(TOKEN)
    suspend fun saveMe(user: User?) = save(USER, user)
    suspend fun loadMe() = load<User>(USER)
}