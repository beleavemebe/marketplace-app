package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository

class GetAllProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke() = productsPreviewRepository.getProducts()
}