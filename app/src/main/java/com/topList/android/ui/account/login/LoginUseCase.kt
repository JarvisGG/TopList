package com.topList.android.ui.account.login

import com.topList.android.App
import com.topList.android.api.NetResult
import com.topList.android.api.getResult
import com.topList.android.api.model.LoginResult
import com.topList.android.api.model.State
import com.topList.android.api.model.Token
import com.topList.android.api.service.AccountService
import java.io.IOException

/**
 * @author yyf
 * @since 04-13-2020
 */
class LoginUseCase (
    val service: AccountService
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ):  NetResult<State<LoginResult>> {


        return try {
            val response = service.login(
                email,
                password
            )
            getResult(response = response,
                onSuccess = {

                },
                onError = {
                NetResult.Error(
                    IOException(response.message())
                )
            })
        } catch (e: Exception) {
            NetResult.Error(IOException("Error register", e))
        }

    }
}