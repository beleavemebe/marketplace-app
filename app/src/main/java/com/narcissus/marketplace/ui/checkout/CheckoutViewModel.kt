package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.domain.card.CardValidateResult
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.ValidateCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
    private val validateCard: ValidateCard,
) : ViewModel() {

    val getCheckoutFlow = getCheckout()

    val getTotalFlow = getCartCost()

    private val _cardValidateFlow = MutableStateFlow<CardValidateResult>(CardValidateResult.Success)

    val cardValidateFlow = _cardValidateFlow.asStateFlow()

    fun checkCard(cardHolder: String, cardNumber: String, cardExpireDate: String, cardCvv: String) {
        _cardValidateFlow.value = validateCard(cardHolder, cardNumber, cardExpireDate, cardCvv)
    }
}
