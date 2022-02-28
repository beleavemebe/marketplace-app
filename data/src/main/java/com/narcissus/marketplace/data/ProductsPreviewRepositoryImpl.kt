package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.SearchParams

class ProductsPreviewRepositoryImpl : ProductsPreviewRepository {
    override suspend fun searchProducts(
        query: String,
        filters: Set<SearchParams.FilterType>
    ): ActionResult<List<ProductPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(): ActionResult<List<ProductPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsRandom(): ActionResult<List<ProductPreview>> {
        return ActionResult.SuccessResult(DummyProducts.previews)
    }

    override suspend fun getProductsTopRated(): ActionResult<List<ProductPreview>> {
        return ActionResult.SuccessResult(DummyProducts.previews)
    }

    override suspend fun getProductsTopSales(): ActionResult<List<ProductPreview>> {
        return ActionResult.SuccessResult(DummyProducts.previews)
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
