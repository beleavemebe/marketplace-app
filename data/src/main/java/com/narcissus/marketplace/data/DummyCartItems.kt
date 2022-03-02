package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.model.ProductPreview

object DummyCartItems {
    private val sampleCartItem = ProductPreview("1", "", 1449, "Apple MacBook Pro 13", "", "", 752, "", "", 3, 152)
    private val sampleCartItemTwo = ProductPreview("2", "", 400, "Apple Watch", "", "", 240, "", "", 5, 100)
    val items: List<CartItem> = listOf(
        CartItem(sampleCartItem, 1),
        CartItem(sampleCartItemTwo, 2),
    )
}
