package com.narcissus.marketplace.usecase

class CheckIfCardNumberIsValid {
    operator fun invoke(cardNumber:Long): Boolean {
        val cardNumberStr = cardNumber.toString()
        var sum: Int = Character.getNumericValue(cardNumberStr[cardNumberStr.length - 1])
        val parity: Int = cardNumberStr.length % 2
        for (i in cardNumberStr.length - 2 downTo 0) {
            var summand: Int = Character.getNumericValue(cardNumberStr[i])
            if (i % 2 == parity) {
                val product = summand * 2
                summand = if (product > 9) product - 9 else product
            }
            sum += summand
        }
        return (sum % 10 == 0)
    }
}