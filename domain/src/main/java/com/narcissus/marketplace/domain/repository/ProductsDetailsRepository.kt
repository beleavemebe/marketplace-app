package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.util.ActionResult

interface ProductsDetailsRepository {
    suspend fun getProductDetailsById(productId: String): ProductDetails
}
