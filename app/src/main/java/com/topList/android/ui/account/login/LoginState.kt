package com.topList.android.ui.account.login

import com.topList.android.api.model.LoginResult

/**
 * @author yyf
 * @since 04-13-2020
 */
sealed class LoginState
class Login(data: LoginResult): LoginState()
object Logout: LoginState()
