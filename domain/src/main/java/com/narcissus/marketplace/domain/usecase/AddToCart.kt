package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.toCartItem
import com.narcissus.marketplace.domain.model.toProductPreview
import com.narcissus.marketplace.domain.repository.CartRepository

class AddToCart(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productPreview: ProductPreview) {
        val cartItem = productPreview.toCartItem()
        cartRepository.addToCart(cartItem)
    }

    suspend operator fun invoke(productDetails: ProductDetails) {
        invoke(productDetails.toProductPreview())
    }
}
