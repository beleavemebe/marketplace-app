package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository

class GetTopRatedProductsByDepartment(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(departmentId: String) =
        productsPreviewRepository.getProductsByDepartmentIdTopRated(departmentId)
}
