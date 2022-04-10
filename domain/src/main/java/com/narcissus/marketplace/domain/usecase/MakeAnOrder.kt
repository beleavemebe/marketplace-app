package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.orders.OrderPaymentResult
import com.narcissus.marketplace.domain.model.orders.OrderPaymentStatus
import com.narcissus.marketplace.domain.model.orders.OrderStatus
import com.narcissus.marketplace.domain.model.orders.toOrder
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.repository.OrderRepository
import java.util.Calendar

class MakeAnOrder(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(orderList: List<CartItem>, orderUUID: String): OrderPaymentResult {
        cartRepository.deleteSelectedItems()
        val paymentResult = orderRepository.payForTheOrder(orderList, orderUUID)
        if (paymentResult.status == OrderPaymentStatus.PAID) {
            orderRepository.saveOrder(
                orderList.toOrder(
                    orderUUID,
                    paymentResult.number!!,
                    Calendar.getInstance().time, OrderStatus.ACCEPTED,
                ),
            )
        }
        return paymentResult
    }
}
