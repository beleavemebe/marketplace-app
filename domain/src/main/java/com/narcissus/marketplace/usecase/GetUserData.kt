package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.UserRepository

class GetUserData(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUserData()
}
