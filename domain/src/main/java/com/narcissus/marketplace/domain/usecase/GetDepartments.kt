package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.DepartmentsRepository

class GetDepartments(private val departmentsRepository: DepartmentsRepository) {
    suspend operator fun invoke() = departmentsRepository.getDepartments()
}
