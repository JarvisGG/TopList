package com.topList.android.ui.account

import com.topList.android.api.model.Token
import com.topList.android.api.model.User
import com.topList.android.utils.Files
import io.reactivex.Completable

/**
 * @author yyf
 * @since 04-13-2020
 */
class AccountManager(private val files: Files) {
    private var token: Token? = null
    private var user: User? = null

    fun getUser(): User? = user

    fun getToken(): Token? = token

    suspend fun syncState(token: Token, user: User) {
        files.saveToken(token)
        files.saveMe(user)
    }


    /**
     * Splash 页用到。恢复登录状态
     */
    suspend fun restore(): Pair<Token?, User?> {
        token = files.loadToken()
        user = files.loadMe()
        return token to user
    }
}