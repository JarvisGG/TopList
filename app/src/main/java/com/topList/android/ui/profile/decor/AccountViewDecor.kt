package com.topList.android.ui.profile.decor

import com.topList.android.api.model.People
import com.topList.android.base.BaseDecor
import com.topList.android.base.IDataRender

/**
 * @author yyf
 * @since 03-19-2020
 */
class AccountViewDecor(
    view: AccountView
) : BaseDecor<AccountView>(view), IDataRender<People> {

    override fun renderData(data: People) {

    }

}