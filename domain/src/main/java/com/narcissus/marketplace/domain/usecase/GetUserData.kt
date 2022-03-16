package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class GetUserData(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUserData()
}
