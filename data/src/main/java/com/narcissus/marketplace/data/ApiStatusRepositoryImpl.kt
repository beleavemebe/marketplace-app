package com.narcissus.marketplace.data

import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.data.mapper.toApiStatus
import com.narcissus.marketplace.domain.model.ApiStatus
import com.narcissus.marketplace.domain.repository.ApiStatusRepository

internal class ApiStatusRepositoryImpl(
    private val apiService: ApiService,
) : ApiStatusRepository {
    override suspend fun getApiStatus(): ApiStatus {
        return apiService.getApiStatus().toApiStatus()
    }
}
