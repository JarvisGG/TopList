package com.topList.android.ui.feed

import androidx.fragment.app.Fragment
import com.topList.theme.base.widget.TToolbar

interface IContainerPager {
    fun installToolBar(toolbar: TToolbar)

    fun currentFragment(): Fragment
}