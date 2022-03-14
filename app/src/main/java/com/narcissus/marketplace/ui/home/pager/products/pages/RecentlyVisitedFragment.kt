package com.narcissus.marketplace.ui.home.pager.products.pages

import com.narcissus.marketplace.ui.home.pager.products.ProductsPagerFragment
import com.narcissus.marketplace.ui.products.ProductListItem
import kotlinx.coroutines.flow.Flow

class RecentlyVisitedFragment : ProductsPagerFragment() {
    override val contentFlow: Flow<List<ProductListItem>>
        get() = viewModel.recentlyVisitedFlow
}
