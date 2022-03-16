package com.narcissus.marketplace.domain.util

data class SearchParams(
    val query: String,
    val filters: Set<FilterType> = setOf(FilterType.None),
    val sortType: SortType = SortType.NONE,
    val sortDirection: SortDirection = SortDirection.ASC
) {

    sealed class FilterType {
        class Rating(val minValue: Int, maxValue: Int) : FilterType()
        class Sales(val minValue: Int, maxValue: Int) : FilterType()
        class Price(val minValue: Int, maxValue: Int) : FilterType()
        object None : FilterType()
    }

    enum class SortType {
        RATING,
        SALES,
        PRICE,
        NONE
    }

    enum class SortDirection {
        DESC,
        ASC
    }
}
