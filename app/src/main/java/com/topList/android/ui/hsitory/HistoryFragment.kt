package com.topList.android.ui.hsitory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.topList.android.R
import com.topList.android.api.model.State
import com.topList.android.base.ui.BasePagingFragment
import com.topList.android.room.HistoryRoomHelper
import com.topList.android.room.model.HistoryEntity
import com.topList.android.ui.LOADING
import com.topList.android.ui.hsitory.domain.HistoryLocalDataSource
import com.topList.android.ui.hsitory.domain.HistoryRepository
import com.topList.android.ui.hsitory.domain.HistoryUseCase
import com.topList.android.ui.hsitory.holder.HistoryHolder
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.toolbar

/**
 * @author yyf
 * @since 04-09-2020
 */
class HistoryFragment : BasePagingFragment() {

    private val vm: HistoryViewModel by viewModels({ this }, { HistoryViewModelFactory (
        HistoryUseCase(
            HistoryRepository(
                HistoryLocalDataSource(HistoryRoomHelper.openHistoryDatabaseForDao(requireContext()))
            )
        )
    ) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun SugarAdapter.Builder.setupHolder(): SugarAdapter.Builder {
        add(HistoryHolder::class.java) { holder ->
            holder.containerView.setOnClickListener {
                findNavController().navigate(HistoryFragmentDirections.actionHistoryToDetail(holder.data.data.url))
            }
        }
        return this
    }

    override fun findRecyclerView() = rvHistory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupVM()
    }

    private fun setupToolbar() {
        toolbar.setTitle("历史记录")
    }

    private fun setupView() {
    }

    private fun setupVM() {
        vm.historyResult.observe(viewLifecycleOwner, Observer {
            when(it) {
                is LOADING -> {
                    progress.visibility = if (it.enable) View.VISIBLE else View.GONE
                }
                is List<*> -> {
                    it as List<HistoryEntity>
                    isRefresh { postLoadMoreSucceed(State(data = it)) }
                }
            }
        })
    }

}