package com.topList.android.ui.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_host.*

/**
 * @author yyf
 * @since 08-13-2019
 */
class HostFragment : BaseFragment() {

    /**
     * overlayFragment 的 host
     */
    private val overlayFragment by lazy {
        NavHostFragment.create(R.navigation.host)
    }

    private val delagate: IMainDelegate by lazy {
        MainDelegate(fragment = this@HostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delagate.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delagate.onViewCreated(view, savedInstanceState)

        if (!overlayFragment.isAdded) {
            parentFragmentManager.beginTransaction()
                .add(R.id.overlayPlace, overlayFragment)
                .setPrimaryNavigationFragment(overlayFragment)
                .commitAllowingStateLoss()
        }
    }

    fun getOverlayNavController(): NavController {
        return overlayFragment.navController
    }

    override fun isSystemUiFullscreen() = true

//    override fun onCreateNavController(navController: NavController) {
//        super.onCreateNavController(navController)
//        navController.navigatorProvider += MainPageFragmentNavigator(requireContext(), childFragmentManager, R.id.hostPlace)
//    }

//    override fun createFragmentNavigator() = FragmentNavigator(requireContext(), childFragmentManager, R.id.hostPlace)


}