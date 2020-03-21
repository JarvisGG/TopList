package com.topList.android.ui.feed

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.topList.android.R
import com.topList.android.api.Apis
import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.base.ui.BasePagingFragment
import com.topList.android.ui.LOADING
import com.topList.android.ui.feed.domain.FeedRemoteDataSource
import com.topList.android.ui.feed.domain.FeedRepository
import com.topList.android.ui.feed.domain.LoadFeedParams
import com.topList.android.ui.feed.domain.LoadFeedUseCase
import com.topList.android.ui.feed.holder.FeedHolder
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.fragment_feed.*
import me.saket.inboxrecyclerview.dimming.TintPainter

/**
 * @author yyf
 * @since 08-09-2019
 */
class FeedFragment : BasePagingFragment() {

    private val vm: FeedViewModel by viewModels({ this }, { FeedViewModelFactory(LoadFeedUseCase(
        FeedRepository(FeedRemoteDataSource(Apis.feed))
    )) })

    override fun SugarAdapter.Builder.setupHolder(): SugarAdapter.Builder {
        add(FeedHolder::class.java)
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupVM()
    }

    override fun findRecyclerView(): RecyclerView = inboxRecyclerview
    override fun findSwipeRefreshLayout(): SwipeRefreshLayout = inboxSwipe

    private fun setupView() {

        inboxRecyclerview.run {
            expandablePage = inboxDetailPage
            tintPainter = TintPainter.uncoveredArea(color = Color.WHITE, opacity = 0.65F)
        }
    }

    private fun setupVM() {
        vm.feedData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is LOADING -> {

                }
                is NetResult.Success<*> -> {
                    isRefresh { postRefreshSucceed(it.data as State<Any>) }
                    isLoading { postLoadMoreSucceed(it.data as State<Any>) }
                }

                is NetResult.Error -> {
                    isRefresh { postRefreshFailed(it.exception) }
                    isLoading { postLoadMoreFailed(it.exception) }
                }
            }

        })
    }

    override fun onRefresh() {
        vm.load(LoadFeedParams(paging.index, false))
    }

    override fun onLoadMore() {
        vm.load(LoadFeedParams(paging.index, false))
    }

}