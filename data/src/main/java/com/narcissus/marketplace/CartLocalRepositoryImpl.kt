package com.narcissus.marketplace

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.local.CartLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartLocalRepositoryImpl : CartLocalRepository {
    override fun getCart(): Flow<List<CartItem>> {
        return flow {
            emit(DummyCartItems.items)
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {

    }

    override suspend fun removeFromCart(cartItem: CartItem) {

    }
}
