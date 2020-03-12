package com.topList.android.ui.profile.holder

import android.view.View
import com.topList.android.R2
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_profile_label.*

/**
 * @author yyf
 * @since 03-12-2020
 */
@Layout(R2.layout.item_profile_label)
class LabelHolder(
    override val containerView: View
) : SugarHolder<LabelData>(containerView), LayoutContainer {

    override fun onBindData(data: LabelData) {
        tv_lable.text = data.title
    }

}