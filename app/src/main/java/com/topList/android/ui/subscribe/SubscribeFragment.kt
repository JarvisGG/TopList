package com.topList.android.ui.subscribe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.topList.android.R
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * @author yyf
 * @since 03-09-2020
 */
class SubscribeFragment : Fragment() {

    private lateinit var vm: SubscribeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpVM()
    }

    private fun setUpVM() {
        vm = activityViewModels<SubscribeViewModel>().value
    }

    private fun initView() {
        tv_title.text = "mo.fish"
    }

}