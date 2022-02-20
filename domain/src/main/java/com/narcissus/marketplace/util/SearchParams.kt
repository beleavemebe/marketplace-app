package com.narcissus.marketplace.util

data class SearchParams(
    val query: String,
    val filterType: Set<FilterType> = setOf(FilterType.Default),
    val sortType: SortType = SortType.Default,
    val sortDirection: SortDirection = SortDirection.Asc
) {
    enum class FilterType {
        Rating,
        Sales,
        Price,
        Default
    }

    enum class SortType {
        Rating,
        Sales,
        Price,
        Default
    }

    enum class SortDirection {
        Desc,
        Asc
    }
}

