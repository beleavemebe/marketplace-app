package com.narcissus.marketplace

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.local.CartLocalRepository
import kotlinx.coroutines.flow.Flow

class CartLocalRepositoryImpl : CartLocalRepository {
    override fun getCart(): Flow<List<CartItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(cartItem: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromCart(cartItem: CartItem) {
        TODO("Not yet implemented")
    }
}
