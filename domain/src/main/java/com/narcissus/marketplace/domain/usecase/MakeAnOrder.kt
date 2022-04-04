package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.OrderPaymentResult
import com.narcissus.marketplace.domain.model.OrderPaymentStatus
import com.narcissus.marketplace.domain.model.OrderStatus
import com.narcissus.marketplace.domain.model.toOrder
import com.narcissus.marketplace.domain.repository.OrderRepository
import java.util.Calendar

class MakeAnOrder(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(orderList: List<CartItem>): OrderPaymentResult {
        val paymentResult = orderRepository.payForTheOrder(orderList)
        if (paymentResult.status == OrderPaymentStatus.PAID) {
            orderRepository.saveOrder(
                orderList.toOrder(
                    paymentResult.number!!,
                    Calendar.getInstance().time, OrderStatus.Accepted,
                ),
            )
        }
        return paymentResult
    }
}
