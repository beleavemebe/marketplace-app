package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository

class GetCart(private val cartRepository: CartRepository) {
    operator fun invoke() = cartRepository.getCart()
}
