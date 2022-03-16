package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.CartRepository

class RemoveSelectedCartItems(private val cartRepository: CartRepository) {
    suspend operator fun invoke() = cartRepository.deleteSelectedItems()
}
