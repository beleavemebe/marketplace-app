package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.card.CardPatterns
import com.narcissus.marketplace.domain.card.CardValidateResult

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

        if (!isExpireDateCorrect(cardExpireDate)) {
            return CardValidateResult.InvalidExpireDate
        }

        if (cardCvv.length < CVV_LENGTH) {
            return CardValidateResult.InvalidCvv
        }

        return CardValidateResult.Success
    }

    // govno code, need to refactor later
    private fun isExpireDateCorrect(cardExpireDate: String): Boolean {
        if (cardExpireDate.isNotBlank() && cardExpireDate.length == EXPIRE_DATE_LENGTH) {
            val isMonthCorrect =
                cardExpireDate[0].digitToInt() * 10 + cardExpireDate[1].digitToInt() < MONTHS &&
                    cardExpireDate[1].digitToInt() != 0
            val isYearCorrect =
                cardExpireDate[3].digitToInt() * 10 + cardExpireDate[4].digitToInt() <= CURRENT_YEAR
            return isMonthCorrect && isYearCorrect
        }
        return false
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
        const val MONTHS = 12
        const val EXPIRE_DATE_LENGTH = 5
        const val CARD_NUMBER_LENGTH = 16
        const val CVV_LENGTH = 3
        const val CURRENT_YEAR = 22
        const val CARD_MAX_DIGIT = 9
    }
}
