package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.util.ActionResult
import com.narcissus.marketplace.domain.util.SearchParams

interface ProductsPreviewRepository {
    suspend fun searchProducts(
        query: String,
        filters: Set<SearchParams.FilterType>
    ): ActionResult<List<ProductPreview>>

    suspend fun getProducts(): List<ProductPreview>
    suspend fun getProductsRandom(): List<ProductPreview>
    suspend fun getProductsTopRated(): List<ProductPreview>
    suspend fun getProductsTopSales(): List<ProductPreview>
    suspend fun getProductsByDepartment(departmentId: String): ActionResult<List<ProductPreview>>
    suspend fun getProductsByDepartmentIdTopRated(departmentId: String): ActionResult<List<ProductPreview>>
    suspend fun getProductsByDepartmentIdTopSales(departmentId: String): ActionResult<List<ProductPreview>>
    suspend fun getSimilarProducts(productId: String): List<ProductPreview>
}
