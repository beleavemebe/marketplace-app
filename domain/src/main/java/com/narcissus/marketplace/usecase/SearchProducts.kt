package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.util.SearchParams
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.SearchParams.SortType as SortType
import com.narcissus.marketplace.util.SearchParams.SortDirection as SortDirection

class SearchProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(searchParams: SearchParams): ActionResult<List<ProductPreview>> {
        val result = productsPreviewRepository.searchProducts(
            query = searchParams.query,
            filters = searchParams.filterType
        )
        val filteredResult: ActionResult<List<ProductPreview>> = when (searchParams.sortType) {
            SortType.Default -> result
            SortType.Rating -> result.mapResult { it.sortedBy { productPreview -> productPreview.rating } }
            SortType.Sales -> result.mapResult { it.sortedBy { productPreview -> productPreview.sales } }
            SortType.Price -> result.mapResult { it.sortedBy { productPreview -> productPreview.price } }
        }
        return if (searchParams.sortDirection == SortDirection.Desc)
            filteredResult.mapResult { it.asReversed() }
        else filteredResult
    }
}
