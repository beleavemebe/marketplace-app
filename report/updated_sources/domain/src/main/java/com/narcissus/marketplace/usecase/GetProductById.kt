package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository

class GetProductById(private val productsDetailsRepository: ProductsDetailsRepository) {
    suspend operator fun invoke(productId: String) =
        productsDetailsRepository.getProductDetailsById(productId)
}
