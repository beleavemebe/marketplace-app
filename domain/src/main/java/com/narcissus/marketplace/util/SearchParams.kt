package com.narcissus.marketplace.util

data class SearchParams(
    val query: String,
    val filters: Set<FilterType> = setOf(FilterType.Default),
    val sortType: SortType = SortType.Default,
    val sortDirection: SortDirection = SortDirection.Asc
) {

    sealed class FilterType {
        class Rating(val minValue: Int, maxValue: Int) : FilterType()
        class Sales(val minValue: Int, maxValue: Int) : FilterType()
        class Price(val minValue: Int, maxValue: Int) : FilterType()
        object Default : FilterType()
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

