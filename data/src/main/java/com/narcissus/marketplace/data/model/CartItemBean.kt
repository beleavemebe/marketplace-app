package com.narcissus.marketplace.data.model

data class CartItemBean(
    var productId: String? = null,
    var productImage: String? = null,
    var productPrice: Int = 0,
    var productName: String? = null,
    var amount: Int? = null,
    var isSelected: Boolean? = null,
)
