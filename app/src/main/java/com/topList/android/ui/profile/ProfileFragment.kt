package com.topList.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topList.android.R
import com.topList.android.api.model.People
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.host.PlaceHolderFragmentDirections
import com.topList.android.ui.profile.ProfileItem.*
import com.topList.android.ui.profile.decor.AccountViewDecor
import com.topList.android.ui.profile.holder.ItemHolder
import com.topList.android.ui.profile.holder.LabelHolder
import com.topList.android.ui.widget.CustomItemDecoration
import com.toplist.android.annotation.Tab
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * @author yyf
 * @since 03-09-2020
 */
@Tab(
    titleRes = R.string.profile,
    iconRes = R.drawable.ic_profile,
    position = 3
)
class ProfileFragment : BaseFragment() {

    private val adapter by lazy {
        SugarAdapter.Builder.with(sProfileList)
            .add(ItemHolder::class.java) { holder ->
                holder.containerView.setOnClickListener {
                    when(holder.data.data) {
                        HISTORY -> findOverlayNavController()?.navigate(PlaceHolderFragmentDirections.actionMainToHistory())
                    }
                }

            }
            .add(LabelHolder::class.java)
            .build()
    }

    private val layoutManager by lazy {
        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private lateinit var accountDecor: AccountViewDecor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        accountDecor = AccountViewDecor(av_header, this)
//        accountDecor.renderData(People(
//            img = "https://tvax1.sinaimg.cn/crop.0.0.1080.1080.180/c0f7eb44ly8g6p5etd3etj20u00u0gnl.jpg?KID=imgbed,tva&Expires=1586513108&ssig=wFUN77bAQD",
//            nickname = "一条漂泊的咸鱼"
//        ))
        accountDecor.renderData(null)

        rv_profile.run {
            adapter = this@ProfileFragment.adapter
            layoutManager = this@ProfileFragment.layoutManager
            addItemDecoration(CustomItemDecoration(context))
        }
    }

    override fun isSystemUiFullscreen() = true
}