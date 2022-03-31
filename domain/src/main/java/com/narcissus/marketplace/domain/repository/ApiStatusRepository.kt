package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.ApiStatus

interface ApiStatusRepository {
    suspend fun getApiStatus(): ApiStatus
}
