package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.domain.model.toCheckoutItem
import com.narcissus.marketplace.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCheckout(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<List<CheckoutItem>> =
        cartRepository.getCart().map { items ->
            items.filter { cartItem ->
                cartItem.isSelected
            }.map {
                it.toCheckoutItem()
            }
        }
}
