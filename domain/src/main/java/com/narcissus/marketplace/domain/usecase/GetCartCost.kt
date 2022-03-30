package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class GetCartCost(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<Int> =
        cartRepository.getCart()
            .mapLatest { items ->
                if (items.isEmpty()) {
                    0
                } else {
                    items.asSequence()
                        .filter { it.isSelected }
                        .map { it.productPrice * it.amount }
                        .reduceOrNull(Int::plus) ?: 0
                }
            }
}
