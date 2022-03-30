package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.DepartmentRepository

class GetDepartments(private val departmentsRepository: DepartmentRepository) {
    suspend operator fun invoke() = departmentsRepository.getDepartments()
}
