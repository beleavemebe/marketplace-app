package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.local.CartLocalRepository

class RemoveFromCart(private val cartLocalRepository: CartLocalRepository) {
    suspend operator fun invoke(cartItem: CartItem) =
        cartLocalRepository.removeFromCart(cartItem)
}