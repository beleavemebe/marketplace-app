package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository

class SetCartItemAmount(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem, amount: Int) =
        cartRepository.setCartItemAmount(cartItem, amount)
}
