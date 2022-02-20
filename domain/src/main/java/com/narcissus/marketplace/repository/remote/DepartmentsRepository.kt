package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.Department

interface DepartmentsRepository {
    suspend fun getDepartments():Result<List<Department>>
}