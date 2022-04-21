package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.search.SortDirection
import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository
import com.narcissus.marketplace.domain.util.ActionResult
import com.narcissus.marketplace.domain.util.SearchParams
import com.narcissus.marketplace.domain.util.SearchParams.SortType as SortType

class SearchProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(searchParams: SearchParams): ActionResult<List<ProductPreview>> {
        val result = productsPreviewRepository.searchProducts(
            query = searchParams.query,
            filters = searchParams.filters
        )
        val filteredResult: ActionResult<List<ProductPreview>> = when (searchParams.sortType) {
            SortType.NONE -> result
            SortType.RATING -> result.mapResult { it.sortedBy { productPreview -> productPreview.rating } }
            SortType.SALES -> result.mapResult { it.sortedBy { productPreview -> productPreview.sales } }
            SortType.PRICE -> result.mapResult { it.sortedBy { productPreview -> productPreview.price } }
        }
        return if (searchParams.sortDirection == SortDirection.DESC)
            filteredResult.mapResult { it.asReversed() }
        else filteredResult
    }
}
