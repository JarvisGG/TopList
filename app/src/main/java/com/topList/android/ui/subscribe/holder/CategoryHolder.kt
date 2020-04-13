package com.topList.android.ui.subscribe.holder

import android.view.View
import com.topList.android.R
import com.topList.android.R2
import com.topList.android.ui.subscribe.model.CategoryItem
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.category_item.*

@Layout(R2.layout.category_item)
class CategoryHolder(
    override val containerView: View
) : SugarHolder<CategoryItem>(containerView), LayoutContainer {

    override fun onBindData(data: CategoryItem) {
        category.text = data.name
        category.setTextColor(if (data.check) {
            context.getColor(R.color.GBK02A)
        } else {
            context.getColor(R.color.GBK04A)
        })
    }
}