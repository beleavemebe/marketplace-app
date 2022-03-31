package com.narcissus.marketplace.data

import com.narcissus.marketplace.apiclient.api.model.ProductPreviewResponseData
import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository
import com.narcissus.marketplace.domain.util.ActionResult
import com.narcissus.marketplace.domain.util.SearchParams

private const val PREVIEWS_AMOUNT = 8

internal class ProductsPreviewRepositoryImpl(
    private val apiService: ApiService,
) : ProductsPreviewRepository {
    override suspend fun searchProducts(
        query: String,
        filters: Set<SearchParams.FilterType>
    ): ActionResult<List<ProductPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(): List<ProductPreview> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsRandom(): List<ProductPreview> {
        val randomProducts = apiService.getRandomProducts(PREVIEWS_AMOUNT, 1).data
        return randomProducts.map(ProductPreviewResponseData::toProductPreview)
    }

    override suspend fun getProductsTopRated(): List<ProductPreview> {
        val randomProducts = apiService.getTopRatedProducts(PREVIEWS_AMOUNT, 1).data
        return randomProducts.map(ProductPreviewResponseData::toProductPreview)
    }

    override suspend fun getProductsTopSales(): List<ProductPreview> {
        val randomProducts = apiService.getTopSalesProducts(PREVIEWS_AMOUNT, 1).data
        return randomProducts.map(ProductPreviewResponseData::toProductPreview)
    }

    override suspend fun getProductsByDepartment(departmentId: String): ActionResult<List<ProductPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByDepartmentIdTopRated(departmentId: String): ActionResult<List<ProductPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByDepartmentIdTopSales(departmentId: String): ActionResult<List<ProductPreview>> {
        TODO("Not yet implemented")
    }
}

fun ProductPreviewResponseData.toProductPreview(): ProductPreview =
    ProductPreview(id, icon, price, name, departmentName, type, stock, color, material, rating, sales)
