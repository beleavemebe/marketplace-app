package com.narcissus.marketplace.domain.model

import java.util.Date

data class Order(
    val number: Int,
    val date: Date,
    val status: OrderStatus,
    val summaryPrice:Int,
    val items: List<OrderItem>,
)

enum class OrderStatus {
    Accepted,
    Completed,
    InDelivering,
    Canceled,
}

data class OrderItem(
    val productId: String,
    val productImage: String,
    val productPrice: Int,
    val productName: String,
    val amount: Int,
    val amountPrice:Int,
)
fun CartItem.toOrderItem():OrderItem = OrderItem(productId,productImage,productPrice,productName,amount,productPrice*amount)


