package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository

class SetCartItemSelected(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem, selected: Boolean) =
        cartRepository.setCartItemSelected(cartItem, selected)
}
