package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository

class GetCartSelectedItemsCostSnapshot(private val cartRepository: CartRepository) {
    suspend operator fun invoke() =
        cartRepository.getCurrentCartSelected().filter { it.isSelected }.sumOf { it.productPrice*it.amount }
}
