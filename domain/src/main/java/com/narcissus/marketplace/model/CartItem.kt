package com.narcissus.marketplace.model

data class CartItem(
    val data: ProductPreview,
    val count: Int,
    val isSelected: Boolean
)


