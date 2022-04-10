package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.data.OrderRepositoryImpl
import com.narcissus.marketplace.data.model.OrderBean
import com.narcissus.marketplace.data.model.OrderItemBean
import com.narcissus.marketplace.domain.model.orders.Order
import com.narcissus.marketplace.domain.model.orders.OrderItem
import com.narcissus.marketplace.domain.model.orders.OrderPaymentStatus

fun OrderBean.toOrder() = runCatching {
    Order(
        id!!,
        number!!,
        date!!,
        status!!,
        summaryPrice!!,
        items!!.map { it.toOrderItem().getOrThrow() },
    )
}.getOrNull()

fun OrderItemBean.toOrderItem() =
    runCatching {
        OrderItem(
            productId!!,
            productImage!!,
            productPrice!!,
            productName!!,
            amount!!,
            amountPrice!!,
        )
    }

fun orderPaymentResponseStatusToOrderPaymentStatus(paymentStatusResponse: String): OrderPaymentStatus {
    return when (paymentStatusResponse) {
        OrderRepositoryImpl.PAYMENT_STATUS_PAID -> OrderPaymentStatus.PAID
        else -> OrderPaymentStatus.CANCELLED
    }
}

