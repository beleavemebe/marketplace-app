package com.narcissus.marketplace.domain.model

data class CartItem(
    val data: ProductPreview,
    val count: Int,
    val isSelected: Boolean
)
