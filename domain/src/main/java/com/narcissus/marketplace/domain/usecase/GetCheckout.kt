package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.domain.model.toCheckoutItem
import com.narcissus.marketplace.domain.repository.CartRepository

class GetCheckout(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): List<CheckoutItem> =
        cartRepository.getSelectedCartItems().map { it.toCheckoutItem() }
}
