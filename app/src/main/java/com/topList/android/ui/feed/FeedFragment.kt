package com.topList.android.ui.feed

import android.app.ActivityOptions
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.topList.android.HostDirections
import com.topList.android.R
import com.topList.android.api.Apis
import com.topList.android.api.NetResult
import com.topList.android.api.model.State
import com.topList.android.base.ui.BasePagingFragment
import com.topList.android.ui.LOADING
import com.topList.android.ui.detail.DetailFragment
import com.topList.android.ui.feed.domain.FeedRemoteDataSource
import com.topList.android.ui.feed.domain.FeedRepository
import com.topList.android.ui.feed.domain.LoadFeedParams
import com.topList.android.ui.feed.domain.LoadFeedUseCase
import com.topList.android.ui.feed.holder.FeedHolder
import com.topList.android.ui.widget.NormalViewScrollJudge
import com.toplist.android.annotation.Tab
import com.zhihu.android.sugaradapter.SugarAdapter
import exhaustive
import kotlinx.android.synthetic.main.fragment_feed.*
import me.saket.inboxrecyclerview.dimming.TintPainter
import me.saket.inboxrecyclerview.page.InterceptResult
import me.saket.inboxrecyclerview.page.OnPullToCollapseInterceptor
import me.saket.inboxrecyclerview.page.SimplePageStateChangeCallbacks

/**
 * @author yyf
 * @since 08-09-2019
 */
@Tab(
    titleRes = R.string.title_home,
    iconRes = R.drawable.ic_feed,
    position = 0
)
class FeedFragment : BasePagingFragment() {

    private var detailFragment: DetailFragment? = null

    private val vm: FeedViewModel by viewModels({ this }, { FeedViewModelFactory(LoadFeedUseCase(
        FeedRepository(FeedRemoteDataSource(Apis.feed))
    )) })

    override fun SugarAdapter.Builder.setupHolder(): SugarAdapter.Builder {
        add(FeedHolder::class.java) { holder ->
            holder.containerView.setOnClickListener {
                detailFragment?.populate(holder.data)
                inboxRecyclerview.expandItem(holder.itemId)
            }
        }
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupPage()
        setupVM()
    }

    override fun findRecyclerView(): RecyclerView = inboxRecyclerview
    override fun findSwipeRefreshLayout(): SwipeRefreshLayout = inboxSwipe

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.main)
        toolbar.setTitle(R.string.app_name)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    findOverlayNavController()?.navigate(HostDirections.actionMainToSearch())
                }
                else -> {}
            }.exhaustive
            true
        }
    }

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

            }
        })

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
        vm.load(LoadFeedParams(paging.index, false))
    }

    override fun onLoadMore() {
        vm.load(LoadFeedParams(paging.index, false))
    }

}