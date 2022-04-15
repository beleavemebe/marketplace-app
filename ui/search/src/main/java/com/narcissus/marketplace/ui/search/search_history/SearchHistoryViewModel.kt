package com.narcissus.marketplace.ui.search.search_history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.ClearSearchHistory
import com.narcissus.marketplace.domain.usecase.GetSearchHistory
import com.narcissus.marketplace.domain.usecase.WriteQueryToSearchHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchHistoryViewModel(
    private val getSearchHistory: GetSearchHistory,
    private val writeQueryToSearchHistory: WriteQueryToSearchHistory,
    private val clearSearchHistory: ClearSearchHistory,
) : ViewModel() {
    val searchHistory = getSearchHistory()
    init{



    }


    //   val searchHistoryViewFlow = searchHistoryFlow.onEach {}.launchIn(CoroutineScope(Dispatchers.Main))
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
