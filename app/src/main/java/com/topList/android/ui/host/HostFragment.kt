package com.topList.android.ui.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.feed.behavior.BottomNavigationBehavior
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
        HostNavFragment.create(R.navigation.host)
    }

    private val delegate: IMainDelegate by lazy {
        MainDelegate(fragment = this@HostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delegate.onViewCreated(view, savedInstanceState)

        if (!overlayFragment.isAdded) {
            parentFragmentManager.beginTransaction()
                .add(R.id.overlayPlace, overlayFragment)
                .setPrimaryNavigationFragment(overlayFragment)
                .commitAllowingStateLoss()
        }
    }

    override fun displayBottomNavigationBar(isShown: Boolean) {
        bottomNav.findBehavior()?.startAnim(bottomNav, isShown)
    }


    fun BottomNavigationView.findBehavior(): BottomNavigationBehavior? {
        var behavior: BottomNavigationBehavior ?= null
        val lp = layoutParams
        if (lp is CoordinatorLayout.LayoutParams) {
            if (lp.behavior is BottomNavigationBehavior) {
                behavior = lp.behavior as BottomNavigationBehavior
            }
        }
        return behavior
    }

    fun getOverlayNavController(): NavController {
        return overlayFragment.navController
    }

    override fun isSystemUiFullscreen() = true
}