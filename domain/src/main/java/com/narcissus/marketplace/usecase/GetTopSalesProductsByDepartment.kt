package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.ProductsPreviewRepository

class GetTopSalesProductsByDepartment(private val productsPreviewRepository: ProductsPreviewRepository) {
    suspend operator fun invoke(departmentId: String) =
        productsPreviewRepository.getProductsByDepartmentIdTopSales(departmentId)
}
