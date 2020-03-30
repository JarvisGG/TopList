package com.topList.android.ui.subscribe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topList.android.R
import com.toplist.android.annotation.Tab
import com.topList.android.api.Apis
import com.topList.android.api.model.SubscribeItem
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.subscribe.domain.SubscribeRemoteDataSource
import com.topList.android.ui.subscribe.domain.SubscribeRepository
import com.topList.android.ui.subscribe.domain.SubscribeUseCase
import com.topList.android.ui.subscribe.holder.CategoryHolder
import com.topList.android.ui.subscribe.holder.StationHeaderHolder
import com.topList.android.ui.subscribe.holder.StationHolder
import com.topList.android.ui.subscribe.model.CategoryItem
import com.topList.android.ui.subscribe.model.Station
import com.topList.android.ui.subscribe.model.StationHeaderUseItem
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
class SubscribeFragment : BaseFragment() {

    private val vm: SubscribeViewModel by viewModels {
        SubscribeViewModelFactory(SubscribeUseCase(SubscribeRepository(SubscribeRemoteDataSource(Apis.subscribe))))
    }

    private val categoryList = arrayListOf<CategoryItem>()
    private val stationList = arrayListOf<Station>()
    private val sourceList = arrayListOf<SubscribeItem>()

    private val categoryAdapter by lazy {
        SugarAdapter.Builder.with(categoryList)
            .add(CategoryHolder::class.java) {  holder ->
                holder.containerView.setOnClickListener {
                    categoryClicked = true
                    onCategoryClick(holder.adapterPosition)
                }
            }
            .build()
    }

    private val stationAdapter by lazy {
        SugarAdapter.Builder.with(stationList)
            .add(StationHeaderHolder::class.java)
            .add(StationHolder::class.java)
            .build()
    }

    private var categoryClicked = false
    private var mMoved = false
    private var targetPosition = -1

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

            stationList.clear()
            stationList.addAll(it.stationList)
            stationAdapter.notifyDataSetChanged()

            sourceList.clear()
            sourceList.addAll(it.sourceList)
        })
    }

    private fun initView() {
        tv_title.text = "mo.fish"

        rv_category.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CustomItemDecoration(context))
        }

        rv_content.apply {
            adapter = stationAdapter
            layoutManager = GridLayoutManager(context, 3).also {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if ((stationAdapter.list[position] as Station).isHeader) 3
                        else 1
                    }
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (mMoved) {
                            mMoved = false
                            val target =
                                targetPosition - (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                            if (0 <= target && target < rv_content.childCount) {
                                val top = rv_content.getChildAt(target).top
                                rv_content.smoothScrollBy(0, top)
                            }
                        } else {
                            if (!categoryClicked) {
                                (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                                val firstVisiblePosition =
                                    (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                                stationAdapter.list[firstVisiblePosition]?.let {
                                    val tag = when (it) {
                                        is StationHeaderUseItem -> {
                                            it.tag
                                        }
                                        is StationUseItem -> {
                                            it.tag
                                        }
                                        else -> {
                                            ""
                                        }
                                    }
                                    moveToTag(tag)
                                }
                            }
                            categoryClicked = false
                        }
                    }

                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (mMoved) {
                        mMoved = false
                        val target = targetPosition - (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                        if (0 <= target && target < rv_content.childCount) {
                            val top = rv_content.getChildAt(target).top
                            rv_content.scrollBy(0, top)
                        }
                    }
                }
            })
        }
    }

    private fun onCategoryClick(position: Int) {
        setCategorySelect(position)
        smoothScrollStationList(position)
        move2Center(position)
    }

    private fun setCategorySelect(position: Int) {
        for ((index, item) in categoryList.withIndex()) {
            item.check = index == position
        }
        categoryAdapter.notifyDataSetChanged()
    }

    private fun smoothScrollStationList(position: Int) {
        var count = 0
        for (index in 0 until position) {
            count += sourceList[index].list.size
        }
        count += position
        targetPosition = count

        rv_content.stopScroll()
        smoothScrollToPosition(count)
    }

    private fun smoothScrollToPosition(position: Int) {
        val manager = rv_content.layoutManager as GridLayoutManager
        val firstVisiblePosition = manager.findFirstVisibleItemPosition()
        val lastVisiblePosition = manager.findLastVisibleItemPosition()

        when {
            position <= firstVisiblePosition -> {
                rv_content.scrollToPosition(position)
            }
            position <= lastVisiblePosition -> {
                val top = rv_content.getChildAt(position-firstVisiblePosition).top
                rv_content.scrollBy(0, top)
            }
            else -> {
                rv_content.scrollToPosition(position)
                mMoved = true
            }
        }
    }

    private fun move2Center(position: Int) {
        val view = rv_category.getChildAt(position -
                (rv_category.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
        view?.let {
            val y = view.top - rv_category.height / 2
            rv_category.smoothScrollBy(0, y)
        }
    }

    private fun moveToTag(tag: String) {
        for ((index, item) in categoryList.withIndex()) {
            if (item.tag == tag) {
                setCategorySelect(index)
                move2Center(index)
                return
            }
        }
    }
}