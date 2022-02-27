package com.narcissus.marketplace

import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
import com.narcissus.marketplace.util.ActionResult

class ProductsDetailsRepositoryImpl : ProductsDetailsRepository {
    override suspend fun getProductDetailsById(productId: String): ActionResult<ProductDetails> {
        TODO("Not yet implemented")
    }
}
