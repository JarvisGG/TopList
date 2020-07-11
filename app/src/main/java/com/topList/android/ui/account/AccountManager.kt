package com.topList.android.ui.account

import com.topList.android.api.model.People
import com.topList.android.api.model.Token
import com.topList.android.utils.Files

/**
 * @author yyf
 * @since 04-13-2020
 */
class AccountManager(private val files: Files) {
    private var token: Token? = null
    private var user: People? = null

    fun getUser(): People? = user

    fun getToken(): Token? = token

    suspend fun syncState(token: Token, user: People) {
        files.saveToken(token)
        files.saveMe(user)
    }


    /**
     * Splash 页用到。恢复登录状态
     */
    suspend fun restore(): Pair<Token?, People?> {
        token = files.loadToken()
        user = files.loadMe()
        return token to user
    }
}