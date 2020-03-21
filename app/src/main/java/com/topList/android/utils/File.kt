package com.topList.android.utils

import android.content.Context
import androidx.core.content.ContextCompat

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
        const val ME = DIR_ACCOUNT + "user.json"
    }

//    fun saveToken(token: AppToken?) = save(TOKEN, token)
//    fun loadToken() = load<AppToken>(TOKEN)
//    fun saveMe(me: Me?) = save(ME, me)
//    fun loadMe() = load<Me>(ME)
}