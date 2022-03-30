package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository

class GetTopSalesProducts(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke() = productsPreviewRepository.getProductsTopSales()
}
