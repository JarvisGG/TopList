package com.topList.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topList.android.R
import com.topList.android.ui.profile.ProfileItem.*
import com.topList.android.ui.profile.decor.AccountViewDecor
import com.topList.android.ui.profile.holder.ItemData
import com.topList.android.ui.profile.holder.ItemHolder
import com.topList.android.ui.profile.holder.LabelData
import com.topList.android.ui.profile.holder.LabelHolder
import com.topList.android.ui.widget.CustomItemDecoration
import com.topList.theme.ThemeManager
import com.topList.theme.ThemeManager.DARK
import com.topList.theme.ThemeManager.LIGHT
import com.zhihu.android.sugaradapter.SugarAdapter
import com.zhihu.android.sugaradapter.SugarHolder
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_profile_setting.*

/**
 * @author yyf
 * @since 03-09-2020
 */
class ProfileFragment : Fragment() {

    private val adapter by lazy {
        SugarAdapter.Builder.with(sProfileList)
            .add(ItemHolder::class.java) {
                when(it.data.data) {
                    THEME -> {

                    }
                    else -> {}
                }
            }
            .add(LabelHolder::class.java)
            .build()
    }

    private val layoutManager by lazy {
        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private lateinit var accountDecor: AccountViewDecor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        accountDecor = AccountViewDecor(av_header)

        rv_profile.run {
            adapter = this@ProfileFragment.adapter
            layoutManager = this@ProfileFragment.layoutManager
            addItemDecoration(CustomItemDecoration(context))
        }
    }

}