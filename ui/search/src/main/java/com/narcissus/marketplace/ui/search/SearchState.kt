package com.narcissus.marketplace.ui.search

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun <H, R> rememberSearchState(
    searchHistory: List<H> = emptyList(),
    searchResults: List<R> = emptyList(),
    onQueryResult: (TextFieldValue) -> List<R>,
): SearchState<H, R> {
    return remember {
        SearchState(
            searchHistory = searchHistory,
            searchResults = searchResults,
        )
    }
}

class SearchState<H, R> internal constructor(
    searchHistory: List<H>,
    searchResults: List<R>,
) {
    var query by mutableStateOf(TextFieldValue())
    var focused by mutableStateOf(false)
    var searching by mutableStateOf(false)
    var searchHistory by mutableStateOf(searchHistory)
    var searchResults by mutableStateOf(searchResults)


    val searchContainer: SearchContainer
        get() = when {
            focused && query.text.isEmpty() -> SearchContainer.History
            searchResults.isEmpty() -> SearchContainer.NoResults
            else -> SearchContainer.Results
        }

}

enum class SearchContainer {
    History, Results, NoResults
}
