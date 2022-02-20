package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.UserRemoteRepository
import com.narcissus.marketplace.util.ActionResult

class AddCard(
    private val userRemoteRepository: UserRemoteRepository,
    private val checkIfCardNumberIsValid: CheckIfCardNumberIsValid
) {
    suspend operator fun invoke(cardNumber: Long): ActionResult<Boolean> {
        return if (checkIfCardNumberIsValid(cardNumber)){
            userRemoteRepository.addCard(cardNumber)
            ActionResult.SuccessResult(true)
        } else ActionResult.ErrorResult("")//подумать, мб делать это не тут

    }
}