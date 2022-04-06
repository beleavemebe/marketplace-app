package com.narcissus.marketplace.domain.model

import java.util.Date

class OrderPaymentResult(
    val id:String?,
    val number: Int?,
    val status: OrderPaymentStatus,
    val message: String,
)

enum class OrderPaymentStatus {
    PAID,
    CANCELLED
}

fun List<CartItem>.toOrder(orderUUID:String,orderNumber: Int, orderDate: Date, orderStatus: OrderStatus): Order =
    Order(
        orderUUID,
        orderNumber,
        orderDate,
        orderStatus,
        this.sumOf { it.amount * it.productPrice },
        this.map { it.toOrderItem() },
    )
