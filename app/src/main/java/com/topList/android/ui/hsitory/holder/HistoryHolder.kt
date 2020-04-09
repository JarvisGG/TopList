package com.topList.android.ui.hsitory.holder

import android.view.View
import com.topList.android.R
import com.topList.android.R2
import com.topList.android.api.model.SearchItem
import com.topList.android.room.model.HistoryEntity
import com.topList.android.utils.TimeFormatUtils
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.feed_item.*

/**
 * @author yyf
 * @since 03-21-2020
 */
@Layout(R2.layout.history_item)
class HistoryHolder(
    override val containerView: View
) : SugarHolder<HistoryEntity>(containerView), LayoutContainer {

    override fun onBindData(data: HistoryEntity) {
        feedItemSubject.text = data.data.title.trim()
        feedItemAvatar.setImageURI(data.data.icon)
    }
}