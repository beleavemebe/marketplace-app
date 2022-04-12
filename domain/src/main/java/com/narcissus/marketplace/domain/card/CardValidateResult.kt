package com.narcissus.marketplace.domain.card

sealed class CardValidateResult {
    object InvalidCardNumber : CardValidateResult()
    object InvalidExpireDate : CardValidateResult()
    object InvalidCardHolderName : CardValidateResult()
    object InvalidCvv : CardValidateResult()
    object Success : CardValidateResult()
}
