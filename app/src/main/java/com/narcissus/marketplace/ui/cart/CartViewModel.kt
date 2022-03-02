package com.narcissus.marketplace.ui.cart

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CartViewModel(
    private val getCart: GetCart = ServiceLocator.getCart,
    private val getCartCost: GetCartCost = ServiceLocator.getCartCost,
    private val getCartItemsAmount:GetCartItemsAmount = ServiceLocator.getCartItemsAmount
) : ViewModel() {

    val getCartFlow = flow {
        getCart().collect { items ->
            emit(items)
        }
    }
    val getCartCostFlow = flow {
        getCartCost().collect { price ->
            emit(price)
        }
    }
    val getCartItemsAmountFlow = flow {
        getCartItemsAmount().collect{ amount ->
            emit(amount)
        }
    }
}