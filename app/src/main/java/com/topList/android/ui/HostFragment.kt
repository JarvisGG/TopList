package com.topList.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigator
import androidx.navigation.fragment.*
import androidx.navigation.ui.NavigationUI
import com.topList.android.R
import com.topList.android.utils.Resource
//import com.topList.android.vm.HostViewModel
import kotlinx.android.synthetic.main.fragment_host.*

/**
 * @author yyf
 * @since 08-13-2019
 */
class HostFragment : NavHostFragment() {

//    private lateinit var vm: HostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationWidget()
        setupVM()
        setupView()
    }

//    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
//        return FragmentNavigator(requireContext(), childFragmentManager, R.id.navHost)
//    }

    private fun setupNavigationWidget() {
//        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)

        NavigationUI.setupWithNavController(bottomNav, navController)
    }


    private fun setupVM() {

//        vm = activityViewModels<HostViewModel>().value
//
//        vm.feedTabsLiveData.observe(viewLifecycleOwner, Observer {
//            when(it) {
//                is Resource.Success -> {
//                    it.data.forEach { tab ->
//                        navView.menu.add(R.id.menu_tabs_group, tab.id, tab.id, tab.name)
//                    }
//                }
//                is Resource.Error -> {
//
//                }
//            }
//        })
//        vm.fetchFeedTabsAsync()
    }

    private fun setupView() {

//        navView.setNavigationItemSelectedListener {
//            vm.drawerSelectedTab.postValue(FeedTab(it.itemId, it.title.toString()))
//            drawerLayout.closeDrawer(navWrapper)
//            true
//        }

        bottomNav.itemIconTintList = null
    }



}