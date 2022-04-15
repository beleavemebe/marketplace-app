package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.card.CardValidateResult
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.ValidateCard
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
    private val validateCard: ValidateCard,
) : ViewModel() {

    private val _cardValidateResultFlow = MutableSharedFlow<CardValidateResult>()
    val cardValidateResultFlow = _cardValidateResultFlow.asSharedFlow()

    private val _screenStateFlow = MutableStateFlow<CheckoutScreenState>(CheckoutScreenState.Loading)
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val items = getCheckout()
            val totalCost = getCartCost()
            _screenStateFlow.emit(
                CheckoutScreenState.Idle(items, totalCost),
            )
        }
    }

    fun checkCard(cardHolder: String, cardNumber: String, cardExpireDate: String, cardCvv: String) {
        viewModelScope.launch {
            val validateResult = validateCard(cardHolder, cardNumber, cardExpireDate, cardCvv)
            _cardValidateResultFlow.emit(validateResult)
        }
    }

    fun onPaymentSuccessful(msg: String) {
        _screenStateFlow.value = CheckoutScreenState.PaymentSuccessful(msg)
    }

    fun onPaymentFailed(msg: String) {
        _screenStateFlow.value = CheckoutScreenState.PaymentFailed(msg)
    }

    fun onPaymentInProgress() {
        _screenStateFlow.value = CheckoutScreenState.Loading
    }
}
