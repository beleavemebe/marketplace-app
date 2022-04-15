package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository

class RestoreCartItems(private val cartRepository: CartRepository) {
    suspend operator fun invoke(orderList: List<CartItem>) =
        cartRepository.addAllSelectedToCart(orderList)
}
