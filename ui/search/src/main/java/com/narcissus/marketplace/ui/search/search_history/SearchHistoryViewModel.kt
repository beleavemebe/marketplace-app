package com.narcissus.marketplace.ui.search.search_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.ClearSearchHistory
import com.narcissus.marketplace.domain.usecase.GetSearchHistory
import com.narcissus.marketplace.domain.usecase.WriteQueryToSearchHistory
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchHistoryViewModel(
    getSearchHistory: GetSearchHistory,
    private val writeQueryToSearchHistory: WriteQueryToSearchHistory,
    private val clearSearchHistory: ClearSearchHistory,
) : ViewModel() {
    val searchHistory = getSearchHistory().map { list ->
            list.map {
                listOf(
                    SearchHistoryItem.HistoryItem(it),
                    SearchHistoryItem.Divider(),
                )
            }.flatten()
    }

    fun writeToSearchHistory(searchQuery: String) {
        viewModelScope.launch {
            writeQueryToSearchHistory(searchQuery)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            clearSearchHistory()
        }
    }
}
