package com.narcissus.marketplace.domain.model

import java.util.Date

data class Order(
    val id: Int,
    val date: Date,
    val status: OrderStatus,
    val items:List<OrderItem>
)

enum class OrderStatus {
    Paid,
    Canceled
}

data class OrderItem(
    val productId: String ="",
    val productImage: String ="",
    val productPrice: Int =0,
    val productName: String="",
    val amount: Int =0
)


