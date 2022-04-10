package com.narcissus.marketplace.domain.model.orders

import com.narcissus.marketplace.domain.model.CartItem
import java.util.Date

data class Order(
    val id:String,
    val number: Int,
    val date: Date,
    val status: OrderStatus,
    val summaryPrice: Int,
    val items: List<OrderItem>,
)


