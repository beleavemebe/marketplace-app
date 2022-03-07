package com.narcissus.marketplace.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import com.narcissus.marketplace.usecase.RemoveFromCart
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCart: GetCart = ServiceLocator.getCart,
    private val getCartCost: GetCartCost = ServiceLocator.getCartCost,
    private val getCartItemsAmount: GetCartItemsAmount = ServiceLocator.getCartItemsAmount,
    private val removeFromCart: RemoveFromCart = ServiceLocator.removeFromCart
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
        getCartItemsAmount().collect { amount ->
            emit(amount)
        }
    }

    fun deleteItem() {
        viewModelScope.launch {

        }
    }

    fun addItem() {
    }

    fun selectAll(isSelected: Boolean) = flow {
        getCart().collect { items ->
            for (element in items) {
                element.isSelected = isSelected
            }
            emit(items)
        }
    }


    fun removeSelectedItems() {

    }
}