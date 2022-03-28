package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class SignInWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pass: String) =
        userRepository.signInWithEmail(email, pass)
}
