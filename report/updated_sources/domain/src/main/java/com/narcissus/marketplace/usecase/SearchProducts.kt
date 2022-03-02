package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.SearchParams
import com.narcissus.marketplace.util.SearchParams.SortDirection as SortDirection
import com.narcissus.marketplace.util.SearchParams.SortType as SortType

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
