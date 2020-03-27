package com.topList.android.ui.host

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.collection.arrayMapOf
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.topList.android.R
import com.topList.android.ui.tab.FragmentParam
import com.topList.android.ui.tab.defaultTabList
import com.toplist.android.ui.tab.TabInfoInject
import kotlinx.android.synthetic.main.fragment_host.*

/**
 * @author yyf
 * @since 03-26-2020
 */

interface ITabUseCase {
    fun setupTabPage()
    fun setupNavigationWidget()
}
class TabUseCase : ITabUseCase {

    private lateinit var host: HostFragment

    private lateinit var delegate: MainDelegate

    operator fun invoke(fragment: HostFragment, dlg: MainDelegate) {
        host = fragment
        delegate = dlg
    }

    private var currentDisplayIndex = 0

    override fun setupTabPage() {

        val manager = host.childFragmentManager
        val transaction = manager.beginTransaction()

        val pagerItemMap = arrayMapOf<String, FragmentParam>()
        for (tabItem in defaultTabList) {
            pagerItemMap[tabItem.getTag()] = tabItem.buildFragmentParam(host.arguments)
        }
        val fragments = manager.fragments

        for (frg in fragments) {
            val tag = frg.tag
            if (!pagerItemMap.containsKey(tag)) {
                transaction.remove(frg)
            } else {
                if (!frg.isAdded) {
                    transaction.add(R.id.hostPlace, frg, tag)
                }
                transaction.hide(frg)
                pagerItemMap.remove(tag)
            }
        }

        for ((tag, param) in pagerItemMap) {
            val frg = manager.fragmentFactory.instantiate(
                host.requireContext().classLoader, param.fragmentClass.name
            )
            frg.arguments = param.argument
            transaction.add(R.id.hostPlace, frg, tag)
            if (param.index == currentDisplayIndex) {
                transaction.show(frg)
            } else {
                transaction.hide(frg)
            }
        }
        transaction.commitNowAllowingStateLoss()
    }

    override fun setupNavigationWidget() {
        val bottomNav = host.bottomNav
        TabInfoInject.injectBottomNavigationViewByMenu(bottomNav)
        bottomNav.setOnNavigationItemSelectedListener { selectItem ->
            val handle = showCurrentPage(selectItem)
            if (handle) {
                val menu: Menu = bottomNav.menu
                var index = 0
                val size = menu.size()
                while (index < size) {
                    val item: MenuItem = menu.getItem(index)
                    item.isChecked = item.order == selectItem.order
                    index++
                }
            }
            val bottomSheetBehavior =
                findBottomSheetBehavior(bottomNav)
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            handle
        }

    }

    private fun findBottomSheetBehavior(view: View): BottomSheetBehavior<*>? {
        val params = view.layoutParams
        if (params !is CoordinatorLayout.LayoutParams) {
            val parent = view.parent
            return if (parent is View) {
                findBottomSheetBehavior(parent as View)
            } else null
        }
        val behavior = params.behavior
        return if (behavior !is BottomSheetBehavior<*>) {
            null
        } else behavior
    }


    private fun showCurrentPage(tab: MenuItem): Boolean {
        try {
            val manager = host.childFragmentManager
            val transaction = manager.beginTransaction()

            val index = tab.order
            val tag = defaultTabList[index].getTag()
            transaction.setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim,
                R.anim.nav_default_pop_enter_anim,
                R.anim.nav_default_pop_exit_anim
            )
            for (frg in manager.fragments) {
                if (tag == frg.tag) {
                    transaction.show(frg)
                } else {
                    transaction.hide(frg)
                }
            }
            transaction.commitAllowingStateLoss()

            currentDisplayIndex = index
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false

        }
    }

}
