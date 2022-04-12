package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.card.CardValidateResult
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.ValidateCard
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
    private val validateCard: ValidateCard,
) : ViewModel() {

    private val _cardValidateFlow = MutableSharedFlow<CardValidateResult>()

    val cardValidateFlow = _cardValidateFlow.asSharedFlow()

    val checkoutFlow = flow {
        emit(getCheckout())
    }

    val totalCostFlow = flow {
        getCartCost().collect{ emit(it) }
    }


    fun checkCard(cardHolder: String, cardNumber: String, cardExpireDate: String, cardCvv: String) {
        viewModelScope.launch {
            val validateResult = validateCard(cardHolder, cardNumber, cardExpireDate, cardCvv)
            _cardValidateFlow.emit(validateResult)
        }
    }
}
