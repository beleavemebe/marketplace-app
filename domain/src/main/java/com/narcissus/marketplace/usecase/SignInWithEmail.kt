package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.UserRepository

class SignInWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pass: String) =
        userRepository.signInWithEmail(email, pass)
}
