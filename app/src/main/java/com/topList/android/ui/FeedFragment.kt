package com.topList.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topList.android.R
import com.topList.android.model.FeedItem
import com.topList.android.ui.FeedFragmentDirections
import com.topList.android.utils.Resource
import com.topList.android.vm.FeedViewModel
//import com.topList.android.vm.HostViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * @author yyf
 * @since 08-09-2019
 */
class FeedFragment : Fragment() {

    private lateinit var feedVm: FeedViewModel
//    private lateinit var hostVm: HostViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupVM()
    }

    private fun setupView() {
        feedList.let {
            it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            it.adapter = InnerAdapter()
        }
    }

    private fun setupVM() {
        feedVm = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        feedVm.feedItemsLiveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    (feedList.adapter as InnerAdapter).apply {
                        clear()
                        addList(it.data)
                    }
                }
                is Resource.Error -> {

                }
            }
        })

//        hostVm = ViewModelProviders.of(requireActivity()).get(HostViewModel::class.java)
//        hostVm.drawerSelectedTab.observe(viewLifecycleOwner, Observer {
//            feedVm.fetchFeedDataById(it.id)
//        })
    }

    inner class InnerAdapter : RecyclerView.Adapter<InnerViewHolder>() {
        private var mList = ArrayList<FeedItem>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InnerViewHolder(containerView = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false))

        override fun getItemCount() = mList.size

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            holder.title.text = mList[position].title
            holder.containerView.setOnClickListener {
                findNavController().navigate(
                    FeedFragmentDirections.actionFeedHostToDetailFragment(
                        mList[position]
                    )
                )
            }
        }


        fun addList(list: ArrayList<FeedItem>) {
            mList.addAll(list)
            notifyDataSetChanged()
        }

        fun clear() = mList.clear()

    }

    inner class InnerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

}