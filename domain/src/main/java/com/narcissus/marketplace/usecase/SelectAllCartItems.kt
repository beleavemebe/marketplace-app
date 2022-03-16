package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.CartRepository

class SelectAllCartItems(private val cartRepository: CartRepository) {
    suspend operator fun invoke(isSelected: Boolean) = cartRepository.selectAllCartItems(isSelected)
}
