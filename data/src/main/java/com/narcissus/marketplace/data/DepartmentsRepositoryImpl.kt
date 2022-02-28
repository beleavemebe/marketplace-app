package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.Department
import com.narcissus.marketplace.repository.remote.DepartmentsRepository
import com.narcissus.marketplace.util.ActionResult

class DepartmentsRepositoryImpl : DepartmentsRepository {
    override suspend fun getDepartments(): ActionResult<List<Department>> {
        TODO("Not yet implemented")
    }
}
