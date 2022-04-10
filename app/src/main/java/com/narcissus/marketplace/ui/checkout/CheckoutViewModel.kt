package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.domain.usecase.GetCartSelectedItemsCostSnapshot
import com.narcissus.marketplace.domain.usecase.GetCheckout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val getCheckout: GetCheckout,
    private val getCartCost: GetCartSelectedItemsCostSnapshot,
) : ViewModel() {
    private val _checkoutFlow = MutableSharedFlow<List<CheckoutItem>>(replay=1)
    val checkoutFlow = _checkoutFlow.asSharedFlow()
    private val _totalCostFlow = MutableSharedFlow<Int>(replay=1)
    val totalCostFlow = MutableSharedFlow<Int>(replay=1).asSharedFlow()
    init {
       viewModelScope.launch(Dispatchers.IO) {
           _checkoutFlow.emit(getCheckout())
           _totalCostFlow.emit(getCartCost())
       }
    }

}
