package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository

class GetAllProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke() = productsPreviewRepository.getProducts()
}
