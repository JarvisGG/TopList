package com.topList.android.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.host.PlaceHolderFragmentDirections
import com.toplist.android.annotation.Tab
import kotlinx.android.synthetic.main.fragment_feed.*

@Tab(
    titleRes = R.string.title_home,
    iconRes = R.drawable.ic_feed,
    position = 0
)
class FeedFragment : BaseFragment() {

    private val fragments = arrayListOf<IContainerPager>(TotalFeedFragment.create(), SubscribeFeedFragment.create())

    private val innerAdapter by lazy {
        object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return fragments[position].currentFragment()
            }

            override fun getItemCount(): Int {
                return fragments.size
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupVP2()
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.main)
        toolbar.setTitle(R.string.app_name)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    findOverlayNavController()?.navigate(PlaceHolderFragmentDirections.actionMainToSearch())
                }
            }
            true
        }

        fragments.forEach { it.installToolBar(toolbar) }
    }

    private fun setupVP2() {
        mainPager.run {
            adapter = innerAdapter
            orientation == ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

}