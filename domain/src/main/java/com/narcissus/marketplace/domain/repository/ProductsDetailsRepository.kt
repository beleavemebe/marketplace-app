package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.ProductDetails

interface ProductsDetailsRepository {
    suspend fun getProductDetailsById(productId: String): ProductDetails
}
