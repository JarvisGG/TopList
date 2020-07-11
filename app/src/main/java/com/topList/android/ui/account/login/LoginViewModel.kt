package com.topList.android.ui.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.topList.android.App
import com.topList.android.api.NetResult
import com.topList.android.api.model.*

/**
 * @author yyf
 * @since 07-09-2020
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun login(
        email: String,
        password: String
    ) = liveData {
        val res = loginUseCase(
            email, password
        )
        when (res) {
            is NetResult.Success<*> -> {
                res.data as State<LoginResult>
                App.INSTANCE?.accountManager?.syncState(
                    Token(res.data.data.token),
                    People().apply {
                        img = res.data.data.avatar
                        nickname = res.data.data.name
                    }
                )
            }
        }
        emit(res)
    }
}

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginUseCase) as T
    }

}