package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.CartRepository

class GetCart(private val cartRepository: CartRepository) {
    suspend operator fun invoke() = cartRepository.getCart()
}
