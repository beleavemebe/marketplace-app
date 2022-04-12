package com.narcissus.marketplace.ui.checkout

import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.domain.model.orders.OrderPaymentResult

sealed class CheckoutScreenState {
    data class Idle(val items: List<CheckoutItem>) : CheckoutScreenState()
    object PaymentInProgress : CheckoutScreenState()
    data class PaymentFailed(val order: OrderPaymentResult) : CheckoutScreenState()
    data class PaymentSuccessful(val order: OrderPaymentResult) : CheckoutScreenState()
}
