package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.auth.Patterns
import com.narcissus.marketplace.domain.auth.SignInResult
import com.narcissus.marketplace.domain.repository.UserRepository

class SignInWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pass: String): SignInResult {
        if (!Patterns.EMAIL.toRegex().matches(email)) {
            return SignInResult.InvalidEmail
        }

        if (pass.isBlank()) {
            return SignInResult.BlankPassword
        }

        return userRepository.signInWithEmail(email, pass)
    }
}
