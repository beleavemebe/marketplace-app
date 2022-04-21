package com.narcissus.marketplace.ui.checkout

import com.narcissus.marketplace.domain.model.CheckoutItem

sealed class CheckoutScreenState {
    object Loading : CheckoutScreenState()
    data class Idle(val items: List<CheckoutItem>, val totalCost: Int) : CheckoutScreenState()
    data class PaymentFailed(val message: String) : CheckoutScreenState()
    data class PaymentSuccessful(val message: String) : CheckoutScreenState()
}
