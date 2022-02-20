package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.ProductDetails

interface ProductsDetailsRepository {
    suspend fun getProductDetailsById(productId:String):Result<ProductDetails>
}