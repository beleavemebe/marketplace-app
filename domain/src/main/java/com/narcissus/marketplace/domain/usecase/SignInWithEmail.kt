package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.util.AuthResult

class SignInWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pass: String): AuthResult {
        return if (email.contains(Regex("@.*\\."))) {
            userRepository.signInWithEmail(email, pass)
        } else {
            AuthResult.WrongEmail
        }
    }
}
