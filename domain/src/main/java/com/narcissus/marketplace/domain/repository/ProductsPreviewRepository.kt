package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.util.ActionResult
import com.narcissus.marketplace.domain.util.SearchParams

interface ProductsPreviewRepository {
    suspend fun searchProducts(
        query: String,
        filters: Set<SearchParams.FilterType>
    ): ActionResult<List<ProductPreview>>

    suspend fun getProducts(): ActionResult<List<ProductPreview>>
    suspend fun getProductsRandom(): ActionResult<List<ProductPreview>>
    suspend fun getProductsTopRated(): ActionResult<List<ProductPreview>>
    suspend fun getProductsTopSales(): ActionResult<List<ProductPreview>>
    suspend fun getProductsByDepartment(departmentId: String): ActionResult<List<ProductPreview>>
    suspend fun getProductsByDepartmentIdTopRated(departmentId: String): ActionResult<List<ProductPreview>>
    suspend fun getProductsByDepartmentIdTopSales(departmentId: String): ActionResult<List<ProductPreview>>
}