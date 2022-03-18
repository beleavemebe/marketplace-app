package com.narcissus.marketplace.domain.model

data class CartItem(
    val productId: String,
    val productImage: String,
    val productPrice: Int,
    val productName: String,
    val amount: Int,
    val isSelected: Boolean
)

fun ProductPreview.toCartItem() =
    CartItem(
        productId = id,
        productImage = icon,
        productPrice = price,
        productName = name,
        amount = 1,
        isSelected = false,
    )
