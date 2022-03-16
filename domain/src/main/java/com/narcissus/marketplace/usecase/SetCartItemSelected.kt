package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.CartRepository

class SetCartItemSelected(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem, selected: Boolean) =
        cartRepository.setCartItemSelected(cartItem, selected)
}
