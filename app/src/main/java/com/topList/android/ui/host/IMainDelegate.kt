package com.topList.android.ui.host

import android.os.Bundle
import android.view.View

/**
 * @author yyf
 * @since 03-26-2020
 */
interface IMainDelegate {

    fun onCreate(savedInstanceState: Bundle?)

    fun onViewCreated(view: View, savedInstanceState: Bundle?)

}