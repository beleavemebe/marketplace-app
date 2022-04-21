package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<List<CartItem>>
    suspend fun addToCart(cartItem: CartItem)
    suspend fun removeFromCart(cartItem: CartItem)
    suspend fun setCartItemSelected(cartItem: CartItem, selected: Boolean)
    suspend fun setCartItemAmount(cartItem: CartItem, amount: Int)
    suspend fun selectAllCartItems(isSelected: Boolean)
    suspend fun deleteSelectedItems()
    suspend fun addAllSelectedToCart(cartItems: List<CartItem>)
    suspend fun getSelectedCartItems(): List<CartItem>
    suspend fun getCartCost(): Int
}
