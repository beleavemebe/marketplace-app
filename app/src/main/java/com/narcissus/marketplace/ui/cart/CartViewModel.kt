package com.narcissus.marketplace.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.usecase.GetCart
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCartItemsAmount
import com.narcissus.marketplace.domain.usecase.RemoveFromCart
import com.narcissus.marketplace.domain.usecase.RemoveSelectedCartItems
import com.narcissus.marketplace.domain.usecase.SelectAllCartItems
import com.narcissus.marketplace.domain.usecase.SetCartItemAmount
import com.narcissus.marketplace.domain.usecase.SetCartItemSelected
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCart: GetCart,
    private val getCartCost: GetCartCost,
    private val getCartItemsAmount: GetCartItemsAmount,
    private val removeFromCart: RemoveFromCart,
    private val setCartItemSelected: SetCartItemSelected,
    private val setCartItemAmount: SetCartItemAmount,
    private val selectAllCartItems: SelectAllCartItems,
    private val removeSelectedCartItems: RemoveSelectedCartItems,
) : ViewModel() {

    val getCartFlow = getCart()

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
        viewModelScope.launch {
            removeSelectedCartItems()
        }
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
