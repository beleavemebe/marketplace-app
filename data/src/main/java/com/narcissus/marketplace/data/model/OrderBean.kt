package com.narcissus.marketplace.data.model

import com.narcissus.marketplace.domain.model.OrderStatus
import java.util.Date

data class OrderBean(
    val id: Int? = null,
    val date: Date? = null,
    val status: OrderStatus? = null,
    val summaryPrice: Int? = null,
    val items: List<OrderItemBean>? = null,
)

data class OrderItemBean(
    val productId: String? = null,
    val productImage: String? = null,
    val productPrice: Int? = null,
    val productName: String? = null,
    val amount: Int? = null,
    val amountPrice: Int? = null
)
