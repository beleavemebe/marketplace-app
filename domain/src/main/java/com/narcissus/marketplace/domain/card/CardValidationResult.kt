package com.narcissus.marketplace.domain.card

sealed class CardValidationResult {
    object InvalidCardNumber : CardValidationResult()
    object InvalidExpirationDate : CardValidationResult()
    object InvalidCardHolderName : CardValidationResult()
    object InvalidCvv : CardValidationResult()
    object Success : CardValidationResult()
}
