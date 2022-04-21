package com.narcissus.marketplace.domain.model

data class CheckoutItem(
    val detailId: String,
    val detailName: String,
    val detailAmount: Int,
    val detailPrice: Int,
)

fun CartItem.toCheckoutItem(): CheckoutItem =
    CheckoutItem(
        detailId = productId,
        detailName = productName,
        detailPrice = amount * productPrice,
        detailAmount = amount,
    )
