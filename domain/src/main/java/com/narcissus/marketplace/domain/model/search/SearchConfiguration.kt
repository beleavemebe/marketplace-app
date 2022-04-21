package com.narcissus.marketplace.domain.model.search

data class SearchConfiguration(
    val query: String,
    val sortBy: SortBy,
    val sortDirection: SortDirection,
    val department: String,
)
