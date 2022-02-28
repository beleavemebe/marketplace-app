package com.narcissus.marketplace.ui.catalog

import androidx.lifecycle.ViewModel

class SearchHistoryViewModel: ViewModel() {

    fun getSearchHistoryList(): List<SearchHistoryModel>{
        return listOf(
            SearchHistoryModel(
               suggestion = "food"
            ),
            SearchHistoryModel(
                suggestion = "nvidia"
            ),
            SearchHistoryModel(
                suggestion = "nike"
            ),
            SearchHistoryModel(
                suggestion = "reebok"
            ),
            SearchHistoryModel(
                suggestion = "hello world"
            ),
        )
    }
}