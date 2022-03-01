package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository

class GetProductDetails(private val productsDetailsRepository: ProductsDetailsRepository) {
    suspend operator fun invoke(productId: String) =
        productsDetailsRepository.getProductDetailsById(productId)
}
