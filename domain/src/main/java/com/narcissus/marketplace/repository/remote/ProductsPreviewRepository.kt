package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.util.SearchParams

interface ProductsPreviewRepository {
    suspend fun searchProducts(
        query: String,
        filters: Set<SearchParams.FilterType>
    ): Result<List<ProductPreview>>

    suspend fun searchProductsTopRated(query: String): Result<List<ProductPreview>>
    suspend fun searchProductsTopSale(query: String): Result<List<ProductPreview>>
    suspend fun getProducts(): Result<List<ProductPreview>>
    suspend fun getProductsRandom(): Result<List<ProductPreview>>
    suspend fun getProductsTopRated(): Result<List<ProductPreview>>
    suspend fun getProductsTopSales(): Result<List<ProductPreview>>
    suspend fun getProductsByDepartment(departmentId: String): Result<List<ProductPreview>>
    suspend fun getProductsByDepartmentIdTopRated(departmentId: String): Result<List<ProductPreview>>
    suspend fun getProductsByDepartmentIdTopSales(departmentId: String): Result<List<ProductPreview>>
}