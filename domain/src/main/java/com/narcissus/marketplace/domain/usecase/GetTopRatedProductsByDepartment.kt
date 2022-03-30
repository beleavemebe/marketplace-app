package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository

class GetTopRatedProductsByDepartment(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(departmentId: String) =
        productsPreviewRepository.getProductsByDepartmentIdTopRated(departmentId)
}
