package com.narcissus.marketplace.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.usecase.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCart: GetCart = ServiceLocator.getCart,
    private val getCartCost: GetCartCost = ServiceLocator.getCartCost,
    private val getCartItemsAmount: GetCartItemsAmount = ServiceLocator.getCartItemsAmount,
    private val removeFromCart: RemoveFromCart = ServiceLocator.removeFromCart,
    private val setCartItemSelected: SetCartItemSelected = ServiceLocator.setCartItemSelected,
    private val setCartItemAmount: SetCartItemAmount = ServiceLocator.setCartItemAmount,
    private val selectAllCartItems: SelectAllCartItems = ServiceLocator.selectAllCartItems
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

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            removeFromCart(cartItem)
        }
    }

    fun selectAll(flag: Boolean) {
        viewModelScope.launch {
            selectAllCartItems(flag)
        }
    }

    fun deleteSelectedItems() {
        TODO()
    }

    fun onItemChecked(cartItem: CartItem, flag: Boolean) {
        viewModelScope.launch {
            setCartItemSelected(cartItem, flag)
        }
    }

    fun onItemAmountChanged(cartItem: CartItem, amount: Int) {
        viewModelScope.launch {
            setCartItemAmount(cartItem, amount)
        }
    }
}
