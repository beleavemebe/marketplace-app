package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.domain.usecase.GetCartSelectedItemsCostSnapshot
import com.narcissus.marketplace.domain.usecase.GetCheckout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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
