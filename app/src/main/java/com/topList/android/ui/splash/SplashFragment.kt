package com.topList.android.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment

/**
 * @author yyf
 * @since 03-23-2020
 *
 */
class SplashFragment : BaseFragment() {
    private val vm: SplashViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.data.observe(viewLifecycleOwner, Observer {
            findMainNavController().navigate(SplashFragmentDirections.actionSplashToMain())
        })
    }

    override fun isSystemUiFullscreen() = true
}