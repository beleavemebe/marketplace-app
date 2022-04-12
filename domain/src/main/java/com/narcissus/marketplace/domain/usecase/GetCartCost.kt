package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository

class GetCartCost(private val cartRepository: CartRepository) {
    suspend operator fun invoke() =
        cartRepository.getCartCost()
}
