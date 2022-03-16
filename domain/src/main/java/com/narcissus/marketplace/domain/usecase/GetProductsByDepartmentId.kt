package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository

class GetProductsByDepartmentId(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(departmentId: String) =
        productsPreviewRepository.getProductsByDepartment(departmentId)
}
