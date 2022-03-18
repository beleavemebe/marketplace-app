package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class GetCartItemsAmount(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<Int> =
        cartRepository.getCart()
            .mapLatest { items ->
                items.count { it.isSelected }
            }
}
