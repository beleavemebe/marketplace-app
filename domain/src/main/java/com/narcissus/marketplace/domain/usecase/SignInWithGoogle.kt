package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository

class SignInWithGoogle(private val userRepository: UserRepository) {
    suspend operator fun invoke(idToken:String) = userRepository.signInWithGoogle(idToken)
}
