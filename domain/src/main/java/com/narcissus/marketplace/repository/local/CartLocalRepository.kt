package com.narcissus.marketplace.repository.local

import com.narcissus.marketplace.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartLocalRepository {
    fun getCart(): Flow<List<CartItem>>
    suspend fun addToCart(cartItem: CartItem)
    suspend fun removeFromCart(cartItem: CartItem)
}