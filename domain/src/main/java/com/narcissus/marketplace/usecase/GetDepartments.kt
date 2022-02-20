package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.DepartmentsRepository

class GetDepartments(private val departmentsRepository: DepartmentsRepository) {
    suspend operator fun invoke() =
        departmentsRepository.getDepartments()
}