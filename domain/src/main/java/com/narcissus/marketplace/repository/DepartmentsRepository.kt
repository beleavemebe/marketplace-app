package com.narcissus.marketplace.repository

import com.narcissus.marketplace.model.Department
import com.narcissus.marketplace.util.ActionResult

interface DepartmentsRepository {
    suspend fun getDepartments(): ActionResult<List<Department>>
}
