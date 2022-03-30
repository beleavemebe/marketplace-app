package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.auth.PasswordRequirement
import com.narcissus.marketplace.domain.auth.Patterns.EMAIL
import com.narcissus.marketplace.domain.auth.SignUpResult
import com.narcissus.marketplace.domain.repository.UserRepository

class SignUpWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(fullName: String, email: String, password: String): SignUpResult {
        if (fullName.isBlank()) {
            return SignUpResult.BlankFullName
        }

        if (email.isBlank() || !EMAIL.toRegex().matches(email)) {
            return SignUpResult.InvalidEmail
        }

        val failedRequirements = PasswordRequirement.findFailedRequirements(password)
        if (failedRequirements.isNotEmpty()) {
            return SignUpResult.InvalidPassword(failedRequirements)
        }

        return userRepository.signUpWithEmail(fullName, email, password)
    }
}
