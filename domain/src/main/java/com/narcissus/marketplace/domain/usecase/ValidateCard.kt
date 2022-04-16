package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.card.CardPatterns
import com.narcissus.marketplace.domain.card.CardValidationResult
import java.util.Calendar

class ValidateCard {
    operator fun invoke(
        cardHolderName: String,
        cardNumber: String,
        expirationDate: String,
        cvv: String,
    ): CardValidationResult {
        return when {
            !isCardHolderValid(cardHolderName) -> CardValidationResult.InvalidCardHolderName
            !isCardNumberValid(cardNumber) -> CardValidationResult.InvalidCardNumber
            !isExpirationDateValid(expirationDate) -> CardValidationResult.InvalidExpirationDate
            !isCvvValid(cvv) -> CardValidationResult.InvalidCvv
            else -> CardValidationResult.Success
        }
    }

    private fun isCardHolderValid(cardHolderName: String) =
        cardHolderName.matches(CardPatterns.CARD_HOLDER_NAME.toRegex())

    private fun isCardNumberValid(cardNumber: String): Boolean {
        val cardParts = cardNumber.replace(" ", "")

        var sum = 0

        if (cardParts.length != CARD_NUMBER_LENGTH) {
            return false
        }

        for (i in cardParts.length - 1 downTo 0 step 2) {
            sum += cardParts[i].digitToInt()
        }

        for (i in cardParts.length - 2 downTo 0 step 2) {
            val oddNumber = (cardParts[i].digitToInt()) * 2
            sum += if (oddNumber > CARD_MAX_DIGIT) oddNumber - CARD_MAX_DIGIT else oddNumber
        }

        return sum % 10 == 0
    }

    private fun isExpirationDateValid(expirationDate: String): Boolean {
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

    private fun isCvvValid(cvv: String) = cvv.length >= CVV_LENGTH

    private companion object {
        const val EXPIRE_DATE_LENGTH = 5
        const val CARD_NUMBER_LENGTH = 16
        const val CVV_LENGTH = 3
        const val CARD_MAX_DIGIT = 9
    }
}
