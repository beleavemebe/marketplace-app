package com.narcissus.marketplace.data

import com.narcissus.marketplace.domain.model.Department
import com.narcissus.marketplace.domain.repository.DepartmentsRepository
import com.narcissus.marketplace.domain.util.ActionResult

internal class DepartmentsRepositoryImpl : DepartmentsRepository {
    override suspend fun getDepartments(): ActionResult<List<Department>> {
        TODO("Not yet implemented")
    }
}
