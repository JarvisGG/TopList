package com.topList.android.ui.search

import android.app.SearchManager
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.style.StyleSpan
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.TransitionRes
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.topList.android.R
import com.topList.android.api.Apis
import com.topList.android.api.NetResult
import com.topList.android.api.model.SearchItem
import com.topList.android.api.model.State
import com.topList.android.base.ui.BasePagingFragment
import com.topList.android.ui.LOADING
import com.topList.android.ui.search.domain.SearchRepository
import com.topList.android.ui.search.domain.SearchUseCase
import com.topList.android.ui.search.holder.SearchHolder
import com.topList.android.utils.ImeUtils
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BasePagingFragment() {

    private val transitions = SparseArray<Transition>()

    private var noResults: TextView? = null

    private val vm: SearchViewModel by viewModels({ this }, { SearchModelFactory (
        SearchUseCase(
            SearchRepository(Apis.search)
        )
    ) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun SugarAdapter.Builder.setupHolder(): SugarAdapter.Builder {
        add(SearchHolder::class.java) { holder ->
            holder.containerView.setOnClickListener {
                findNavController().navigate(SearchFragmentDirections.actionSearchToDetail(holder.data.url))
            }
        }
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupVM()
    }

    override fun onResume() {
        super.onResume()
        invalidateStatusBar()
    }

    override fun findRecyclerView() = searchResults

    private fun setupView() {
        container.setOnClickListener {  }
        setupSearchView()
    }

    private fun setupSearchView() {
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryHint = getString(R.string.search_hint)
            inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
            imeOptions = imeOptions or
                    EditorInfo.IME_ACTION_SEARCH or
                    EditorInfo.IME_FLAG_NO_EXTRACT_UI or
                    EditorInfo.IME_FLAG_NO_FULLSCREEN
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchFor(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    if (TextUtils.isEmpty(query)) {
                        clearResults()
                    }
                    return true
                }
            })
            setOnQueryTextFocusChangeListener { _, hasFocus ->

            }

        }

        searchView.post {
            searchView.requestFocus()
            ImeUtils.showIme(searchView)
        }
    }

    internal fun searchFor(query: String) {
        clearResults()
        ImeUtils.hideIme(searchView)
        searchView.clearFocus()
        isLoading = true
        vm.search(query)
    }

    internal fun clearResults() {
        TransitionManager.beginDelayedTransition(
            container,
            getTransition(R.transition.auto)
        )
        searchResults.visibility = View.GONE
        resultsScrim.visibility = View.GONE
        setNoResultsVisibility(View.GONE)
    }

    private fun setNoResultsVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) {
            if (noResults == null) {
                noResults = stubNoSearchResults.inflate() as TextView
                noResults?.apply {
                    setOnClickListener {
                        searchView.setQuery("", false)
                        searchView.requestFocus()
                        ImeUtils.showIme(searchView)
                    }
                }
            }
            val message = String.format(
                getString(R.string.no_search_results), searchView.query.toString()
            ).toSpannable()
            message[message.indexOf('“') + 1, message.length - 1] = StyleSpan(Typeface.ITALIC)
            noResults?.apply { text = message }
        }
        noResults?.apply { this.visibility = visibility }
    }

    private fun getTransition(@TransitionRes transitionId: Int): Transition? {
        var transition: Transition? = transitions.get(transitionId)
        if (transition == null) {
            transition = TransitionInflater.from(requireContext()).inflateTransition(transitionId)
            transitions.put(transitionId, transition)
        }
        return transition
    }

    private fun setupVM() {
        vm.searchResult.observe(viewLifecycleOwner, Observer {
            when(it) {
                is LOADING -> {
                    progress.visibility = if (it.enable) View.VISIBLE else View.GONE
                }
                is NetResult.Success<*> -> {
                    it.data as State<List<SearchItem>>
                    if (it.data.data.isNotEmpty()) {
                        showResultUI()
                    } else {
                        hideResultUI()
                    }
                    isLoading { postLoadMoreSucceed(it.data as State<Any>) }
                }

                is NetResult.Error -> {
                    hideResultUI()
                    isLoading { postLoadMoreFailed(it.exception) }
                }
            }
        })
    }

    private fun showResultUI() {
        if (searchResults.visibility != View.VISIBLE) {
            TransitionManager.beginDelayedTransition(
                container,
                getTransition(R.transition.search_show_results)
            )
            searchResults.visibility = View.VISIBLE
        }
    }

    private fun hideResultUI() {
        TransitionManager.beginDelayedTransition(
            container, getTransition(R.transition.auto)
        )
        setNoResultsVisibility(View.VISIBLE)
    }
}
