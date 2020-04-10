package com.topList.android.ui.profile.decor

import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.facebook.drawee.view.SimpleDraweeView
import com.topList.android.R
import com.topList.android.api.model.People
import com.topList.android.base.BaseDecor
import com.topList.android.base.IDataRender
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.PlaceHolderFragmentDirections

/**
 * @author yyf
 * @since 03-19-2020
 */
class AccountViewDecor(
    private val root: AccountView,
    private val fragment: BaseFragment
) : BaseDecor<AccountView>(root), IDataRender<People> {

    override fun renderData(data: People?) {
        root.removeAllViews()
        if (data == null) {
            showGuestState()
        } else {
            showAccountState(data)
        }
    }

    private fun showGuestState() {
        LayoutInflater.from(root.context).inflate(R.layout.widget_guest, root, true)
        val setup = root.findViewById<AppCompatTextView>(R.id.setup)
        setup.setOnClickListener {
            fragment.findOverlayNavController()?.navigate(PlaceHolderFragmentDirections.actionMainToLogin())
        }
    }

    private fun showAccountState(data: People) {
        LayoutInflater.from(root.context).inflate(R.layout.widget_account, root, true)
        val avatar = root.findViewById<SimpleDraweeView>(R.id.avatar)
        avatar.setImageURI(data.img)
        val name = root.findViewById<AppCompatTextView>(R.id.name)
        name.text = data.nickname
        root.setOnClickListener {

        }
    }




}