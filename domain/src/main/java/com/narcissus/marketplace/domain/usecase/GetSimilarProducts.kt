package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository

class GetSimilarProducts(
    private val productsPreviewRepository: ProductsPreviewRepository,
) {
    suspend operator fun invoke(id: String) =
        productsPreviewRepository.getSimilarProducts(id)
}
