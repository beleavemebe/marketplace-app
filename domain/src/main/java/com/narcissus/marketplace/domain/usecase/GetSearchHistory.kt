package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class GetSearchHistory(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.searchHistory
}
