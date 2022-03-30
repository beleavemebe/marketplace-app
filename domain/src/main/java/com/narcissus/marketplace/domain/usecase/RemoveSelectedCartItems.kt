package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository

class RemoveSelectedCartItems(private val cartRepository: CartRepository) {
    suspend operator fun invoke() = cartRepository.deleteSelectedItems()
}
