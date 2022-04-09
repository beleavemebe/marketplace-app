package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository

class GetCartSnapshot(private val cartRepository: CartRepository) {
        suspend operator fun invoke() = cartRepository.getCurrentCartSelected()
}
