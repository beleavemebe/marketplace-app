package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.ApiStatusRepository

class GetApiStatus(private val apiStatusRepository: ApiStatusRepository) {
    suspend operator fun invoke() =
        apiStatusRepository.getApiStatus()
}
