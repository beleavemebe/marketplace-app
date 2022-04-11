package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.domain.card.CardValidateResult
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.ValidateCard
import kotlinx.coroutines.flow.flow

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
    private val validateCard: ValidateCard,
) : ViewModel() {

    lateinit var cardValidateResult: CardValidateResult

    val checkoutFlow = flow {
        emit(getCheckout())
    }
    val totalCostFlow = getCartCost()

    fun checkCard(cardHolder: String, cardNumber: String, cardExpireDate: String, cardCvv: String) {
        cardValidateResult = validateCard(cardHolder, cardNumber, cardExpireDate, cardCvv)
    }
}
