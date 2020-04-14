package com.topList.android.ui.account.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.topList.android.App
import com.topList.android.api.NetResult
import com.topList.android.api.model.LoginResult
import com.topList.android.api.model.State
import com.topList.android.api.model.Token
import com.topList.android.api.model.User
import com.topList.android.ui.account.register.domain.RegisterUseCase
import com.topList.android.ui.account.register.domain.VerifyUseCase

/**
 * @author yyf
 * @since 04-11-2020
 */
class RegisterViewModel(
    private val verifyUseCase: VerifyUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    fun sendCode(code: String) = liveData {
        emit(verifyUseCase(code))
    }

    fun register(
        name: String,
        email: String,
        code: String,
        password: String
    ) = liveData {
        val res = registerUseCase(
            name, email, code, password
        )
        when (res) {
            is NetResult.Success<*> -> {
                res.data as State<LoginResult>
                App.INSTANCE?.accountManager?.syncState(
                    Token(res.data.data.token),
                    User(res.data.data.avatar, res.data.data.nickname)
                )
            }
        }
        emit(res)
    }
}

class RegisterViewModelFactory(
    private val verifyUseCase: VerifyUseCase,
    private val registerUseCase: RegisterUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(verifyUseCase, registerUseCase) as T
    }

}