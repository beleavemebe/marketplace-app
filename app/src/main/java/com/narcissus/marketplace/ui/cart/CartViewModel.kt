package com.narcissus.marketplace.ui.cart

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.usecase.GetCart
import kotlinx.coroutines.flow.flow

class CartViewModel(
    private val getCart: GetCart = ServiceLocator.getCart // temporary only getCart
) : ViewModel() {

    val getCartFlow = flow {
        val result = getCart()
        emit(result)
    }
}