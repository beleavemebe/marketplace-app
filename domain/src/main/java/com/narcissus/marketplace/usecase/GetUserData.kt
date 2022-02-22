package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.repository.remote.UserRemoteRepository

class GetUserData(private val userRemoteRepository: UserRemoteRepository) {
    suspend operator fun invoke() = userRemoteRepository.getUserData()
}