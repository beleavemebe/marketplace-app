package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.Department

interface DepartmentRepository {
    suspend fun getDepartments(): List<Department>
}
