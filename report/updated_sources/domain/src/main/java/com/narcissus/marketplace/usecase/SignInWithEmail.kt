package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.UserRemoteRepository

class SignInWithEmail(private val userRemoteRepository: UserRemoteRepository) {
    suspend operator fun invoke(email: String, pass: String) =
        userRemoteRepository.signInWithEmail(email, pass)
}
