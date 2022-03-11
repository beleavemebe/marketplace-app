package com.narcissus.marketplace.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        val state = rememberSearchState(
            searchHistory = viewModel.searchHistoryList,
        ) { query: TextFieldValue ->
            viewModel.getResultList(query.text)
        }

        SearchBar(
            query = state.query,
            onQueryChange = { state.query = it },
            onSearchFocusChange = { state.focused = it },
            onClearQuery = { state.query = TextFieldValue("") },
            onBack = { state.query = TextFieldValue("") },
            searching = state.searching,
            focused = state.focused,
            modifier = modifier
        )

        when (state.searchContainer) {

            SearchContainer.NoResults -> {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("No Results!", fontSize = 24.sp, color = Color.Black)
                }
            }

            SearchContainer.History -> {
                SearchHistoryLayout(searchHistoryList = state.searchHistory) {
                    var text = state.query.text
                    if (text.isEmpty()) text = it else text += " $it"
                    text.trim()
                    state.query = TextFieldValue(text, TextRange(text.length))
                }
            }

            SearchContainer.Results -> {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun SearchHistoryLayout(
    modifier: Modifier = Modifier,
    searchHistoryList: List<SearchHistoryModel>,
    onSuggestionClick: (String) -> Unit,
) {
    Column(
        modifier = modifier.padding(4.dp),
    ) {
        searchHistoryList.forEach { searchHistoryModel ->
            ItemSearchHistory(
                modifier = Modifier.padding(4.dp),
                searchHistory = searchHistoryModel,
                onClick = {
                    onSuggestionClick(it.suggestion)
                },
                onCancel = {

                }
            )
        }
    }
}

@Composable
fun ItemSearchHistory(
    modifier: Modifier = Modifier,
    searchHistory: SearchHistoryModel,
    onClick: ((SearchHistoryModel) -> Unit)? = null,
    onCancel: ((SearchHistoryModel) -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick?.run {
                    invoke(searchHistory)
                }
            }
            .padding(vertical = 8.dp, horizontal = 10.dp),
    ) {
        Text(
            text = searchHistory.suggestion,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(end = 8.dp),
        )
        IconButton(
            onClick = {
                onCancel?.run {
                    invoke(searchHistory)
                }
            },
            modifier = Modifier
                .size(16.dp)
                .padding(1.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = Color(0xFFE0E0E0),
                contentDescription = null,
            )
        }
    }
}


