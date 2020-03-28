package com.topList.android.ui.subscribe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.topList.android.R
import com.toplist.android.annotation.Tab
import com.topList.android.api.Apis
import com.topList.android.ui.subscribe.domain.SubscribeRemoteDataSource
import com.topList.android.ui.subscribe.domain.SubscribeRepository
import com.topList.android.ui.subscribe.domain.SubscribeUseCase
import com.topList.android.ui.subscribe.holder.CategoryHolder
import com.topList.android.ui.subscribe.model.CategoryItem
import com.topList.android.ui.subscribe.model.StationUseItem
import com.topList.android.ui.widget.CustomItemDecoration
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * @author yyf
 * @since 03-09-2020
 */
@Tab(
    titleRes = R.string.subscribe,
    iconRes = R.drawable.ic_subscribe,
    position = 1
)
class SubscribeFragment : Fragment() {

    private val vm: SubscribeViewModel by viewModels {
        SubscribeViewModelFactory(SubscribeUseCase(SubscribeRepository(SubscribeRemoteDataSource(Apis.feed))))
    }

    private val categoryList = arrayListOf<CategoryItem>()
    private val stationList = arrayListOf<StationUseItem>()

    private val categoryAdapter by lazy {
        SugarAdapter.Builder.with(categoryList)
            .add(CategoryHolder::class.java)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpVM()
    }

    private fun setUpVM() {
        vm.fetchSubscribeItems().observe(viewLifecycleOwner, Observer {
            categoryList.clear()
            categoryList.addAll(it.categoryList)
            categoryAdapter.notifyDataSetChanged()
        })
    }

    private fun initView() {
        tv_title.text = "mo.fish"

        rv_category.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CustomItemDecoration(context))
        }
    }
}