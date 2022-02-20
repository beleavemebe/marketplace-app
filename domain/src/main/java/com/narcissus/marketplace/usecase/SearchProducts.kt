package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.util.SearchParams
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository
import com.narcissus.marketplace.util.SearchParams.SortType as SortType
import com.narcissus.marketplace.util.SearchParams.SortDirection as SortDirection

class SearchProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(searchParams: SearchParams): Result<List<ProductPreview>> {
        val result = productsPreviewRepository.searchProducts(
            query = searchParams.query,
            filters = searchParams.filterType
        )
        val filteredResult: Result<List<ProductPreview>> = when (searchParams.sortType) {
            SortType.Default -> result
            SortType.Rating -> result.map { it.sortedBy { productPreview -> productPreview.rating } }
            SortType.Sales -> result.map { it.sortedBy { productPreview -> productPreview.sales } }
            SortType.Price -> result.map { it.sortedBy { productPreview -> productPreview.price } }
        }
        return if (searchParams.sortDirection == SortDirection.Desc)
            filteredResult.map { it.asReversed() }
        else filteredResult
    }
}
