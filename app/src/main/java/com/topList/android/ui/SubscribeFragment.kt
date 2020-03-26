package com.topList.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.topList.android.R
import com.toplist.android.annotation.Tab

/**
 * @author yyf
 * @since 03-09-2020
 */
@Tab(
    titleRes = R.string.subscribe,
    iconRes = R.drawable.ic_subscribe,
    position = 1
)
class SubscribeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe, container, false)
    }

}