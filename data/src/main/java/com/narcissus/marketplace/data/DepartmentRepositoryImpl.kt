package com.narcissus.marketplace.data

import com.narcissus.marketplace.apiclient.api.model.DepartmentResponseData
import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.data.mapper.toDepartment
import com.narcissus.marketplace.domain.model.Department
import com.narcissus.marketplace.domain.repository.DepartmentRepository

internal class DepartmentRepositoryImpl(
    private val apiService: ApiService,
) : DepartmentRepository {
    override suspend fun getDepartments(): List<Department> {
        return apiService.getDepartments().data
            .map(DepartmentResponseData::toDepartment)
    }
}
