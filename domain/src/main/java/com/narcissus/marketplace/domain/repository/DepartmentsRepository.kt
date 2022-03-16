package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.Department
import com.narcissus.marketplace.domain.util.ActionResult

interface DepartmentsRepository {
    suspend fun getDepartments(): ActionResult<List<Department>>
}
