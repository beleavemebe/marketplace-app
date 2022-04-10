package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.domain.model.toCheckoutItem
import com.narcissus.marketplace.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCheckout(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): List<CheckoutItem> =
        cartRepository.getCurrentCartSelected().map {it.toCheckoutItem()}
}
