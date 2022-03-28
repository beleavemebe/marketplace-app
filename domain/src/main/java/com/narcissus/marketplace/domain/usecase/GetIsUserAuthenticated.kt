package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class GetIsUserAuthenticated(private val userRepository: UserRepository) {
    suspend operator fun invoke() =
        userRepository.isUserAuthenticated()
}
