package com.topList.android.ui.subscribe.holder

import android.view.View
import com.topList.android.R2
import com.topList.android.ui.subscribe.model.StationHeaderUseItem
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.station_header_item.*

@Layout(R2.layout.station_header_item)
class StationHeaderHolder (
    override val containerView: View
) : SugarHolder<StationHeaderUseItem>(containerView), LayoutContainer {
    override fun onBindData(data: StationHeaderUseItem) {
//        category.text = data.name
    }
}