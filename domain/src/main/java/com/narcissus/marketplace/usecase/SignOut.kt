package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.UserRemoteRepository

class SignOut(private val userRemoteRepository: UserRemoteRepository) {
    suspend operator fun invoke() = userRemoteRepository.signOut()
}