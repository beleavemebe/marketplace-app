package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.ProductsPreviewRepository

class GetRandomProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke() = productsPreviewRepository.getProductsRandom()
}
