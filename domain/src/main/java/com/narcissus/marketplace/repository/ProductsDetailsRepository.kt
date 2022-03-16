package com.narcissus.marketplace.repository

import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.util.ActionResult

interface ProductsDetailsRepository {
    suspend fun getProductDetailsById(productId: String): ActionResult<ProductDetails>
}
