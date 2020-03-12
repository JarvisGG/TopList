package com.topList.android.ui.profile.holder

import android.view.View
import com.topList.android.R2
import com.topList.theme.ThemeManager
import com.topList.theme.base.widget.TImageView
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_profile_setting.*

/**
 * @author yyf
 * @since 03-12-2020
 */
@Layout(R2.layout.item_profile_setting)
class ItemHolder(
    override val containerView: View
) : SugarHolder<ItemData>(containerView), LayoutContainer {

    override fun onBindData(data: ItemData) {
        iv_item as TImageView
        iv_item.setImageResource(data.data.iconRes)

        if (data.data.isSwitch) {
            sw_item.visibility = View.VISIBLE
            iv_arrow.visibility = View.GONE
            sw_item.setOnClickListener {
                if (sw_item.isChecked) {
                    ThemeManager.switchThemeTo(ThemeManager.DARK)
                } else {
                    ThemeManager.switchThemeTo(ThemeManager.LIGHT)
                }
            }
        } else {
            sw_item.visibility = View.GONE
            iv_arrow.visibility = View.VISIBLE
            sw_item.setOnClickListener { }
        }


        tv_item.text = data.data.title
        sw_item.isChecked = ThemeManager.isDark()
    }

}