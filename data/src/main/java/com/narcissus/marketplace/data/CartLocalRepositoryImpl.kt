package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.local.CartLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CartLocalRepositoryImpl : CartLocalRepository {
    override fun getCart(): Flow<List<CartItem>> {
        return flow {
            emit(DummyCartItems.items)
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromCart(cartItem: CartItem) {
        TODO("Not yet implemented")
    }
}
