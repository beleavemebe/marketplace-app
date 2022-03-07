package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.repository.local.CartLocalRepository

class SetCartItemAmount(private val cartLocalRepository: CartLocalRepository) {
    suspend operator fun invoke(cartItem: CartItem, amount: Int) =
        cartLocalRepository.setCartItemAmount(cartItem, amount)
}
