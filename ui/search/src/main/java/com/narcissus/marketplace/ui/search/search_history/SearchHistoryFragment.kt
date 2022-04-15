package com.narcissus.marketplace.ui.search.search_history

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.core.navigation.destination.SearchDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.domain.model.search.SortBy
import com.narcissus.marketplace.ui.search.R
import com.narcissus.marketplace.ui.search.databinding.FragmentSearchHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchHistoryFragment : Fragment(R.layout.fragment_search_history) {
    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchHistoryViewModel by viewModel()
    private val historyAdapter = AsyncListDifferDelegationAdapter(
        SearchHistoryItem.DIFF_CALLBACK,
        SearchHistoryItem.HistoryItem.delegate(),
        SearchHistoryItem.Divider.delegate()
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
        historyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initListeners(){
        binding.tvSearchHistoryClearAll.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    private fun observeSearchHistory() {
        lifecycleScope.launchWhenCreated {
            viewModel.searchHistory.collect { searchHistory ->
               if(searchHistory.isNotEmpty()){
                   val items:MutableList<SearchHistoryItem> = mutableListOf()
                   searchHistory.map { SearchHistoryItem.HistoryItem(it) }.forEach {
                       items.addAll(listOf(it,SearchHistoryItem.Divider()))
                   }
                   items.add(0,SearchHistoryItem.Divider())
                   historyAdapter.items = items
               }else{
                   historyAdapter.items = listOf()
               }

            }
        }
    }

    private fun writeQueryToSearchHistory(query: String) {
        viewModel.writeToSearchHistory(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
