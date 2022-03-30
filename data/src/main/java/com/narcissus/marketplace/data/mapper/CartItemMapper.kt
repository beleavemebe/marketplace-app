package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.data.model.CartItemBean
import com.narcissus.marketplace.domain.model.CartItem

fun CartItemBean.toCartItem(): CartItem? =
    runCatching {
        CartItem(
            productId!!,
            productImage!!,
            productPrice,
            productName!!,
            amount!!,
            isSelected!!
        )
    }.getOrDefault(null)

fun CartItem.toBean(): CartItemBean =
    CartItemBean(productId, productImage, productPrice, productName, amount, isSelected)
