package com.topList.android.base.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IntRange
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.topList.android.api.model.State
import com.topList.android.base.model.Paging
import com.zhihu.android.sugaradapter.SugarAdapter

/**
 * @author yyf
 * @since 03-21-2020
 */
abstract class BasePagingFragment : BaseFragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: SugarAdapter

    open var paging: Paging = Paging(0)

    private var isRefresh: Boolean = false
        set(value) {
            field = value
            recyclerView.post { swipeRefreshLayout.isRefreshing = value }
        }

    fun isRefresh(action: () -> Unit) {
        if (isRefresh) {
            action()
        }
    }

    private var isLoading: Boolean = false

    fun isLoading(action: () -> Unit) {
        if (isLoading) {
            action()
        }
    }

    protected var data = arrayListOf<Any>()
    /**
     * 初始化 holder 配置
     */
    abstract fun SugarAdapter.Builder.setupHolder(): SugarAdapter.Builder

    /**
     * 提供 itemDecoration
     */
    protected open fun provideItemDecoration(): ItemDecoration? {
        return null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SugarAdapter.Builder.with(data).setupHolder().build()
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = findRecyclerView()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        provideItemDecoration()?.let {
            recyclerView.addItemDecoration(it)
        }
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isScrollingTriggerLoadingMore()) {
                    loadMore()
                }
            }
        })
        swipeRefreshLayout = findSwipeRefreshLayout()
        swipeRefreshLayout.setOnRefreshListener { refresh() }

        refresh()
    }

    /**
     * 设置 rv
     */
    abstract fun findRecyclerView(): RecyclerView

    abstract fun findSwipeRefreshLayout(): SwipeRefreshLayout

    private fun refresh() {
        swipeRefreshLayout.post {
            if (checkFragmentDetached()) {
                return@post
            }
            paging.index = 0
            isRefresh = true
            onRefresh()
        }
    }

    open fun onRefresh() {

    }

    @UiThread
    protected open fun postRefreshSucceed(result: State<Any>?) {
        if (checkFragmentDetached()) return
        isRefresh = false
        data.clear()
        data.addAll(result!!.data)
        adapter.notifyDataSetChanged()
    }

    @UiThread
    protected open fun postRefreshFailed(throwable: Throwable?) {
        if (checkFragmentDetached()) return
        isRefresh = false
        throwable?.printStackTrace()
    }


    protected open fun isScrollingTriggerLoadingMore(): Boolean {
        val layoutManager: RecyclerView.LayoutManager = recyclerView.layoutManager!!
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition: Int
        lastVisibleItemPosition = if (LinearLayoutManager::class.java.isInstance(layoutManager)) {
            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        } else {
            0
        }
        return totalItemCount > 0 && totalItemCount - lastVisibleItemPosition - 1 <= getScrollLoadMoreThreshold()
    }

    /**
     * 滑动时不足 5 条触发加载下一页
     */
    @IntRange(from = 0)
    protected open fun getScrollLoadMoreThreshold() = 5

    private fun loadMore() {
        recyclerView.post {
            if (checkFragmentDetached() || isLoading) return@post
            isLoading = true
            paging.index += 1
            onLoadMore()
        }
    }

    open fun onLoadMore() {

    }

    @UiThread
    protected open fun postLoadMoreFailed(throwable: Throwable?) {
        if (checkFragmentDetached()) return
        isLoading = false
        throwable?.printStackTrace()
    }

    @UiThread
    protected open fun postLoadMoreSucceed(result: State<Any>?) {
        if (checkFragmentDetached()) return
        isLoading = false
        if (result?.data != null && result.page != -1) {
            insertDataRangeToList(data.size, result.data as List<Any>)
        }
    }

    protected fun insertDataRangeToList(start: Int, dataSet: List<Any>?) {
        if (start >= 0 && start <= data.size && dataSet != null && dataSet.isNotEmpty()) {
            data.addAll(start, dataSet)
            adapter.notifyItemRangeInserted(start, dataSet.size)
        }
    }

    protected fun removeDataRangeFromList(start: Int, count: Int) {
        if (start >= 0 && start < data.size && start + count <= data.size) {
            data.subList(start, start + count).clear()
            adapter.notifyItemRangeRemoved(start, count)
        }
    }

    private fun checkFragmentDetached() =  activity == null || !isAdded

}