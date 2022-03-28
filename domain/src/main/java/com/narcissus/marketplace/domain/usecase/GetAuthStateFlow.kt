package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class GetAuthStateFlow(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.authStateFlow
}
