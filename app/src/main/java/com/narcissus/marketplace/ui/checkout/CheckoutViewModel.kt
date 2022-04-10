package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.domain.usecase.GetCartSelectedItemsCostSnapshot
import com.narcissus.marketplace.domain.usecase.GetCheckout
import kotlinx.coroutines.flow.flow

class CheckoutViewModel(
    private val getCheckout: GetCheckout,
    private val getCartCost: GetCartSelectedItemsCostSnapshot,
) : ViewModel() {

    val checkoutFlow = flow {
        emit(getCheckout())
    }
    val totalCostFlow = flow {
        emit(getCartCost())
    }
}
