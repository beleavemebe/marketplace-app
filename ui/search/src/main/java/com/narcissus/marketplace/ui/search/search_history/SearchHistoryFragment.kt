package com.narcissus.marketplace.ui.search.search_history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.core.navigation.destination.SearchDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.domain.model.search.SortBy
import com.narcissus.marketplace.ui.search.R
import com.narcissus.marketplace.ui.search.databinding.FragmentSearchHistoryBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchHistoryFragment : Fragment(R.layout.fragment_search_history) {
    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchHistoryViewModel by viewModel()
    private val historyAdapter = AsyncListDifferDelegationAdapter(
        SearchHistoryItem.DIFF_CALLBACK,
        SearchHistoryItem.HistoryItem.delegate(),
        SearchHistoryItem.Divider.delegate(),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)
        binding.searchView.root.setOnClickListener {
            navigator.navigate(
                SearchDestination("Query", "Department", SortBy.RATING),
            )
        }
        initRecyclerView()
        observeSearchHistory()
        initListeners()
    }

    private fun initRecyclerView() {
        val historyRecyclerView = binding.rvSearchHistory
        historyRecyclerView.adapter = historyAdapter
    }

    private fun initListeners() {
        binding.tvSearchHistoryClearAll.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    private fun observeSearchHistory() {
        viewModel.searchHistory.onEach { searchHistory ->
            if (searchHistory.isNotEmpty()) {
                historyAdapter.items = searchHistory.toMutableList().apply {
                    add(
                        0,
                        SearchHistoryItem.Divider(),
                    )
                }
            } else {
                historyAdapter.items = emptyList()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun writeQueryToSearchHistory(query: String) {
        viewModel.writeToSearchHistory(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
