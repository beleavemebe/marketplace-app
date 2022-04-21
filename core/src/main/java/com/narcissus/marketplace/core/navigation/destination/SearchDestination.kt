package com.narcissus.marketplace.core.navigation.destination

import com.narcissus.marketplace.domain.model.search.SortBy

class SearchDestination(
    query: String,
    department: String? = null,
    sortBy: SortBy = SortBy.DO_NOT_SORT,
) : NavDestination {
    override val url = buildString {
        append("marketplace-app://search?query=$query")
        append(";sort_by=${sortBy.code}")
        if (department != null) append(";department=$department")
    }
}
