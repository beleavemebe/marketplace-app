package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.data.model.CartItemBean
import com.narcissus.marketplace.domain.model.CartItem

fun CartItemBean.toCartItem(): CartItem? =
    runCatching {
        CartItem(
            data!!.toProductPreview(),
            count!!,
            isSelected!!
        )
    }.getOrDefault(null)
