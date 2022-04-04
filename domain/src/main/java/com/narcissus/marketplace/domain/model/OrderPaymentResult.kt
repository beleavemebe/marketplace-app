package com.narcissus.marketplace.domain.model

import java.util.Date

class OrderPaymentResult (
    val number:Int?,
    val status:OrderPaymentStatus,
    val message:String
    )
enum class OrderPaymentStatus{
    PAID,
    CANCELLED
}
fun List<CartItem>.toOrder(orderId:Int,orderDate:Date,orderStatus: OrderStatus):Order=
    Order(
        orderId,
        orderDate,
        orderStatus,
        this.sumOf { it.amount*it.productPrice },
        this.map { it.toOrderItem() }
    )

