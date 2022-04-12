package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.card.CardPatterns
import com.narcissus.marketplace.domain.card.CardValidateResult
import java.util.Calendar

class ValidateCard {
    operator fun invoke(
        cardHolderName: String,
        cardNumber: String,
        cardExpireDate: String,
        cardCvv: String,
    ): CardValidateResult {

        if (!cardHolderName.matches(CardPatterns.CARD_HOLDER_NAME.toRegex())) {
            return CardValidateResult.InvalidCardHolderName
        }

        if (!isCardNumberCorrect(cardNumber.replace(" ", ""))) {
            return CardValidateResult.InvalidCardNumber
        }

        if (!isExpirationDateCorrect(cardExpireDate)) {
            return CardValidateResult.InvalidExpirationDate
        }

        if (cardCvv.length < CVV_LENGTH) {
            return CardValidateResult.InvalidCvv
        }

        return CardValidateResult.Success
    }

    private fun isExpirationDateCorrect(expirationDate: String): Boolean {
        return when {
            expirationDate.isBlank() -> false
            expirationDate.length != EXPIRE_DATE_LENGTH -> false
            else -> {
                val monthAndYear = expirationDate.split("/")
                val month = monthAndYear[0].toInt()
                val year = monthAndYear[1].toInt()

                val isMonthCorrect = month in 1..12

                val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
                val isYearCorrect = year >= currentYear

                isMonthCorrect && isYearCorrect
            }
        }
    }

    private fun isCardNumberCorrect(cardNumber: String): Boolean {
        var sum = 0

        if (cardNumber.length != CARD_NUMBER_LENGTH) {
            return false
        }

        for (i in cardNumber.length - 1 downTo 0 step 2) {
            sum += cardNumber[i].digitToInt()
        }

        for (i in cardNumber.length - 2 downTo 0 step 2) {
            val oddNumber = (cardNumber[i].digitToInt()) * 2
            sum += if (oddNumber > CARD_MAX_DIGIT) oddNumber - CARD_MAX_DIGIT else oddNumber
        }

        return sum % 10 == 0
    }

    private companion object {
        const val EXPIRE_DATE_LENGTH = 5
        const val CARD_NUMBER_LENGTH = 16
        const val CVV_LENGTH = 3
        const val CARD_MAX_DIGIT = 9
    }
}
