package com.topList.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.*
import androidx.navigation.plusAssign
import androidx.navigation.ui.NavigationUI
import com.topList.android.R
import com.topList.android.navigator.MainPageFragmentNavigator
import kotlinx.android.synthetic.main.fragment_host.*

/**
 * @author yyf
 * @since 08-13-2019
 */
class HostFragment : NavHostFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationWidget()
    }

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider += MainPageFragmentNavigator(requireContext(), childFragmentManager, R.id.hostPlace)
    }

    override fun createFragmentNavigator() = FragmentNavigator(requireContext(), childFragmentManager, R.id.hostPlace)

    private fun setupNavigationWidget() {
        NavigationUI.setupWithNavController(bottomNav, navController)
    }
}