package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class WriteQueryToSearchHistory(private val userRepository: UserRepository) {
    suspend operator fun invoke(searchQuery:String) = userRepository.writeToSearchHistory(searchQuery)
}
