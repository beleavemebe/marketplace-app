package com.narcissus.marketplace.data.model

data class CartItemBean(
    var productId: String? = null,
    var productImage: String? = null,
    var productPrice: Int = 0,
    var productName: String? = null,
    var amount: Int = 0,
    var isSelected: Boolean = false,
    var stock: Int? = null
)
