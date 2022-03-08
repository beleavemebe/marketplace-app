package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.local.CartLocalRepository

class RemoveSelectedCartItems(private val cartLocalRepository: CartLocalRepository) {
    suspend operator fun invoke() = cartLocalRepository.deleteSelectedItems()
}
