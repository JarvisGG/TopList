package com.topList.android.utils

import java.util.regex.Pattern

object FormatChecker {
    private const val EMAIL_REX = "^[\\w-.!#\$%&]+@[a-z0-9._-]+\\.[a-z]+\$"
    fun checkEmailFormat(email: String): Boolean {
        return Pattern.compile(EMAIL_REX).matcher(email).matches()
    }

    fun checkPasswordFormat(password: String): Boolean {
        return "[a-zA-Z]".toRegex().containsMatchIn(password)
                && "[0-9]".toRegex().containsMatchIn(password)
                && "[`~!@#$%^&*()_+=\\[{\\]}\\|;:'\",<.>/?-]".toRegex().containsMatchIn(password)
                && ".{10,256}".toRegex().containsMatchIn(password)
    }
}