package com.narcissus.marketplace.data.model

data class CartItemBean @JvmOverloads constructor(
    var data: ProductPreviewBean? = null,
    var count: Int? = null,
    var isSelected: Boolean? = null,
)
