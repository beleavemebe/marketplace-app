package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.util.AuthResult

class SignUpWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(fullName: String, email: String, password: String): AuthResult {
        if (fullName.isBlank()){
            return AuthResult.SignUpEmptyInput
        }

        if (email.isBlank() || !EMAIL.toRegex().matches(email)) {
            return AuthResult.SignUpWrongEmail
        }

        if (password.isBlank() || password.length < MIN_LENGTH) {
            return AuthResult.SignUpToShortPassword
        }

        return userRepository.signUpWithEmail(fullName, email, password)
    }

    private companion object {
        const val EMAIL = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
        const val MIN_LENGTH = 6
    }
}
