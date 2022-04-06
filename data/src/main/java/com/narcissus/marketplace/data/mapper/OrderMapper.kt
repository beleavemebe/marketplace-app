package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.data.model.OrderBean
import com.narcissus.marketplace.data.model.OrderItemBean
import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.model.OrderItem

fun OrderBean.toOrder() = runCatching {
    Order(
        id!!,
        number!!,
        date!!,
        status!!,
        summaryPrice!!,
        items!!.map { it.toOrderItem() },
    )
}.getOrNull()

fun OrderItemBean.toOrderItem() = OrderItem(
    productId!!,
    productImage!!,
    productPrice!!,
    productName!!,
    amount!!,
    amountPrice!!,
)
