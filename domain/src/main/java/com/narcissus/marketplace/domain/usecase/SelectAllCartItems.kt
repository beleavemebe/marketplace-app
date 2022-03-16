package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository

class SelectAllCartItems(private val cartRepository: CartRepository) {
    suspend operator fun invoke(isSelected: Boolean) = cartRepository.selectAllCartItems(isSelected)
}
