package com.narcissus.marketplace.domain.model.search

enum class SortBy(
    val code: String
) {
    RATING("rating"),
    SALES("sales"),
    PRICE("price"),
    DO_NOT_SORT("do_not_sort"),
}
