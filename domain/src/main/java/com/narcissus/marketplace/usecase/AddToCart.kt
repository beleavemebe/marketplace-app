package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.CartRepository

class AddToCart(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) = cartRepository.addToCart(cartItem)
}
