package com.topList.android.ui.account.register.domain

import com.topList.android.api.service.AccountService

/**
 * @author yyf
 * @since 04-13-2020
 */
class VerifyUseCase  (
    val service: AccountService
) {
    suspend operator fun invoke(
        email: String
    ) = service.sendEmailCode(
        email
    )
}