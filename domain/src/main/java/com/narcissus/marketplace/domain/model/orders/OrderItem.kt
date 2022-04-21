package com.narcissus.marketplace.domain.model.orders

import com.narcissus.marketplace.domain.model.CartItem

data class OrderItem(
    val productId: String,
    val productImage: String,
    val productPrice: Int,
    val productName: String,
    val amount: Int,
    val amountPrice: Int,
)
fun CartItem.toOrderItem(): OrderItem =
    OrderItem(productId, productImage, productPrice, productName, amount, productPrice * amount)
