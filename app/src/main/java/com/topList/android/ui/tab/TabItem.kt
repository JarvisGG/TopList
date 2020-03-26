package com.topList.android.ui.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.topList.android.R
import com.topList.android.ui.DiscoverFragment
import com.topList.android.ui.SubscribeFragment
import com.topList.android.ui.feed.FeedFragment
import com.topList.android.ui.profile.ProfileFragment

/**
 * @author yyf
 * @since 03-26-2020
 */
private val KEY_GRAPH_ID: String = "android-support-nav:fragment:graphId"

val defaultTabList = arrayListOf(
    TabItem.FEED,
    TabItem.SUBSCRIBE,
    TabItem.DISCOVER,
    TabItem.PROFILE
).apply {
    sortBy {
        it.index
    }
}

enum class TabItem(
    val fragmentClass: Class<out Fragment>,
    val graphId: Int,
    val index: Int
) {


    FEED(FeedFragment::class.java, R.navigation.feed, 0),

    SUBSCRIBE(SubscribeFragment::class.java, R.navigation.subscribe, 1),

    DISCOVER(DiscoverFragment::class.java, R.navigation.discover, 2),

    PROFILE(ProfileFragment::class.java, R.navigation.profile, 3);

    fun getTag(): String = KEY_TAG + fragmentClass.name

    fun buildFragmentParam(argument: Bundle?): FragmentParam = FragmentParam(
        fragmentClass, Bundle(argument).apply {
            putInt(KEY_GRAPH_ID, graphId)
        }, index
    )

}


val KEY_TAG = "toplist:host_pager:pager"

data class FragmentParam(val fragmentClass: Class<out Fragment>, val argument: Bundle?, val index: Int)