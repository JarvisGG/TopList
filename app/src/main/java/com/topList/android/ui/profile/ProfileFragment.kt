package com.topList.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topList.android.R
import com.topList.android.accountManager
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.common.CommonViewModel
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

    private val cvm: CommonViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initVM()
        initData()
    }

    private fun initView() {

        accountDecor = AccountViewDecor(av_header, this)

        rv_profile.run {
            adapter = this@ProfileFragment.adapter
            layoutManager = this@ProfileFragment.layoutManager
            addItemDecoration(CustomItemDecoration(context))
        }
    }

    /**
     * 渲染头像相关数据
     */
    private fun initData() {
        accountManager.getUser()?.run {
            accountDecor.renderData(this)
        } ?: accountDecor.renderData(null)
    }

    private fun initVM() {
        cvm.accountState.observe(viewLifecycleOwner, Observer {
            accountDecor.renderData(it)
        })
    }



    override fun isSystemUiFullscreen() = true
}