package com.narcissus.marketplace.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.auth.AuthState
import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.usecase.GetAuthStateFlow
import com.narcissus.marketplace.domain.usecase.GetCart
import com.narcissus.marketplace.domain.usecase.GetCartCostFlow
import com.narcissus.marketplace.domain.usecase.GetCartItemsAmount
import com.narcissus.marketplace.domain.usecase.RemoveFromCart
import com.narcissus.marketplace.domain.usecase.RemoveSelectedCartItems
import com.narcissus.marketplace.domain.usecase.SelectAllCartItems
import com.narcissus.marketplace.domain.usecase.SetCartItemAmount
import com.narcissus.marketplace.domain.usecase.SetCartItemSelected
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CartViewModel(
    getCart: GetCart,
    getCartCostFlow: GetCartCostFlow,
    getCartItemsAmount: GetCartItemsAmount,
    getAuthStateFlow: GetAuthStateFlow,
    private val removeFromCart: RemoveFromCart,
    private val setCartItemSelected: SetCartItemSelected,
    private val setCartItemAmount: SetCartItemAmount,
    private val selectAllCartItems: SelectAllCartItems,
    private val removeSelectedCartItems: RemoveSelectedCartItems,
) : ViewModel() {

    val cartFlow = getCart()

    val cartCostFlow = getCartCostFlow()

    val itemAmountFlow = getCartItemsAmount()

    private val _loadingFlow = MutableStateFlow(true)
    val loadingFlow = _loadingFlow.asStateFlow()
    val a = cartFlow.onEach {
        Log.d("DEBUG", "CART OBSERVED")
        delay(1000)
        _loadingFlow.emit(false)
    }
        .launchIn(viewModelScope)
    val isSelectAllCheckboxActive =
        cartFlow.mapLatest { cartItems ->
            cartItems.all(CartItem::isSelected)
        }

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            Log.d("DEBUG", "ITEM DELETED")
            _loadingFlow.emit(true)
            removeFromCart(cartItem)
        }
    }

    fun selectAll(flag: Boolean) {
        viewModelScope.launch {
            Log.d("DEBUG", "SELECTED ALL")
            _loadingFlow.emit(true)
            selectAllCartItems(flag)
        }
    }

    fun deleteSelectedItems() {
        viewModelScope.launch {
            Log.d("DEBUG", "DELETE SELECTED")
            _loadingFlow.emit(true)
            removeSelectedCartItems()
        }
    }

    fun onItemChecked(cartItem: CartItem, flag: Boolean) {
        viewModelScope.launch {
            Log.d("DEBUG", "ITEM CHECKED")
            _loadingFlow.emit(true)
            setCartItemSelected(cartItem, flag)
        }
    }

    fun onItemAmountChanged(cartItem: CartItem, amount: Int) {
        viewModelScope.launch {
            Log.d("DEBUG", "AMOUNT CHANGED")
            _loadingFlow.emit(true)
            setCartItemAmount(cartItem, amount)
        }
    }

    val isCheckoutButtonActive = combine(
        getAuthStateFlow(),
        cartFlow,
    ) { authState, cartItems ->
        authState is AuthState.Authenticated && cartItems.any { cartItem -> cartItem.isSelected }
    }
}
