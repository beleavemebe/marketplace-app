package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository

class RemoveFromCart(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) = cartRepository.removeFromCart(cartItem)
}
