package com.topList.android.ui.subscribe.holder

import android.view.View
import com.topList.android.R2
import com.topList.android.ui.subscribe.model.StationUseItem
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.station_item.*

@Layout(R2.layout.station_item)
class StationHolder (
    override val containerView: View
) : SugarHolder<StationUseItem>(containerView), LayoutContainer {
    override fun onBindData(data: StationUseItem) {
        iv_station.setImageURI(data.img)
        tv_station_name.text = data.name
    }

}