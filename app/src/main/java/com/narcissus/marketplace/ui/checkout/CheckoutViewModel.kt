package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
) : ViewModel() {

    val getCheckoutFlow = getCheckout()

    val getTotalFlow = getCartCost()
}
