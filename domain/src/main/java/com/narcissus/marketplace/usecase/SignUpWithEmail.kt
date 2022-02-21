package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.UserRemoteRepository
import com.narcissus.marketplace.util.ActionResult

class SignUpWithEmail(private val userRemoteRepository: UserRemoteRepository) {
    suspend operator fun invoke(email: String, pass: String): ActionResult.SuccessResult<Boolean> {
        userRemoteRepository.signUpWithEmail(email, pass)
        //TODO алгоритм проверки валидности логина/пароля
        return ActionResult.SuccessResult(true)
    }

}