package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository

class GetProductsByDepartmentId(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(departmentId: String) =
        productsPreviewRepository.getProductsByDepartment(departmentId)
}