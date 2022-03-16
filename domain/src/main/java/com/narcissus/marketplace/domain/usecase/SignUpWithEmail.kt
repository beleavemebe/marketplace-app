package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.util.ActionResult

class SignUpWithEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pass: String): ActionResult.SuccessResult<Boolean> {
        userRepository.signUpWithEmail(email, pass)
        // TODO алгоритм проверки валидности логина/пароля
        return ActionResult.SuccessResult(true)
    }
}
