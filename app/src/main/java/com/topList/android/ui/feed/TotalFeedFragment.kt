package com.topList.android.ui.feed

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.topList.android.R
import com.topList.android.api.Apis
import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.base.ui.BasePagingFragment
import com.topList.android.ui.LOADING
import com.topList.android.ui.collect.CollectRecorder
import com.topList.android.ui.detail.DetailFragment
import com.topList.android.ui.feed.domain.FeedRemoteDataSource
import com.topList.android.ui.feed.domain.FeedRepository
import com.topList.android.ui.feed.domain.LoadFeedParams
import com.topList.android.ui.feed.domain.LoadFeedUseCase
import com.topList.android.ui.feed.holder.FeedHolder
import com.topList.android.ui.widget.NormalViewScrollJudge
import com.zhihu.android.sugaradapter.SugarAdapter
import com.topList.android.ui.hsitory.HistoryRecorder
import com.topList.theme.base.widget.TToolbar
import kotlinx.android.synthetic.main.feed_item.view.*
import kotlinx.android.synthetic.main.fragment_total_feed.inboxDetailPage
import kotlinx.android.synthetic.main.fragment_total_feed.inboxRecyclerview
import kotlinx.android.synthetic.main.fragment_total_feed.inboxSwipe
import me.saket.inboxrecyclerview.dimming.TintPainter
import me.saket.inboxrecyclerview.page.InterceptResult
import me.saket.inboxrecyclerview.page.OnPullToCollapseInterceptor
import me.saket.inboxrecyclerview.page.SimplePageStateChangeCallbacks

/**
 * @author yyf
 * @since 08-09-2019
 */
class TotalFeedFragment : BasePagingFragment(), IContainerPager {

    companion object {
        fun create() = TotalFeedFragment()
    }

    private var detailFragment: DetailFragment? = null

    private var toolbar: TToolbar? = null

    private var interceptBackEvent = false
        set(value) {
            feedBackPressedCallback.isEnabled = value
            field = value
        }

    private val feedBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (inboxDetailPage.isExpanded) {
                if (detailFragment?.canGoBack() == true) {
                    detailFragment?.goBack()
                    return
                }
                inboxRecyclerview.collapse()
                detailFragment?.block()
                displayBottomNavigationBar(true)
            }
        }
    }


    private val vm: FeedViewModel by viewModels({ this }, { FeedViewModelFactory(
        LoadFeedUseCase(
            FeedRepository(FeedRemoteDataSource(Apis.feed))
        )
    ) })

    override fun SugarAdapter.Builder.setupHolder(): SugarAdapter.Builder {

        add(FeedHolder::class.java) { holder ->
            holder.containerView.setOnClickListener {
                detailFragment?.populate(holder.data.url, true)
                inboxRecyclerview.expandItem(holder.itemId)
                displayBottomNavigationBar(false)
                HistoryRecorder.record(holder.data, lifecycleScope, requireContext())
            }
            holder.containerView.feedCollect.setOnClickListener {
                holder.containerView.feedCollect.setImageResource(R.drawable.ic_collected)
                Toast.makeText(requireActivity(), "收藏成功", Toast.LENGTH_SHORT).show()
                vm.collect(holder.data.id).observe(viewLifecycleOwner, Observer { })
                HistoryRecorder.record(holder.data, lifecycleScope, requireContext())
            }
        }
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_total_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupPage()
        setupVM()
    }

    override fun findRecyclerView(): RecyclerView = inboxRecyclerview
    override fun findSwipeRefreshLayout(): SwipeRefreshLayout? = inboxSwipe

    private fun setupView() {

        inboxDetailPage.pullToCollapseInterceptor = object : OnPullToCollapseInterceptor {
            override fun invoke(event: MotionEvent, downX: Float, downY: Float, upwardPull: Boolean): InterceptResult {
                val canScrollFurther = if (upwardPull) {
                    NormalViewScrollJudge.canScrollDown(inboxDetailPage, event, downX, downY, false)
                } else {
                    NormalViewScrollJudge.canScrollUp(inboxDetailPage, event, downX, downY, false)
                }
                return if (canScrollFurther) InterceptResult.INTERCEPTED else InterceptResult.IGNORED
            }
        }

        inboxDetailPage.addStateChangeCallbacks(object: SimplePageStateChangeCallbacks() {
            override fun onPageCollapsed() {
                interceptBackEvent = false
            }

            override fun onPageExpanded() {
                interceptBackEvent = true
            }
        })

        toolbar?.run {
            inboxDetailPage.pushParentToolbarOnExpand(this)
        }

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

    private fun setupPage() {
        detailFragment = childFragmentManager.findFragmentById(inboxDetailPage.id) as DetailFragment?
        if (detailFragment == null) {
            detailFragment = DetailFragment()
        }

        childFragmentManager
            .beginTransaction()
            .replace(inboxDetailPage.id, detailFragment!!)
            .commitNowAllowingStateLoss()
    }

    override fun onRefresh() {
        vm.load(LoadFeedParams(paging.index, true))
    }

    override fun onLoadMore() {
        vm.load(LoadFeedParams(paging.index, true))
    }


    override fun createOnBackPressedCallback(): OnBackPressedCallback? {
        return feedBackPressedCallback
    }

    override fun installToolBar(toolbar: TToolbar) {
        this.toolbar = toolbar
    }

    override fun currentFragment() = this

    override fun isSystemUiFullscreen() = true

}
