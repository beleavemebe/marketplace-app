package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.OrderPaymentResult
import com.narcissus.marketplace.domain.model.OrderPaymentStatus
import com.narcissus.marketplace.domain.model.OrderStatus
import com.narcissus.marketplace.domain.model.toOrder
import com.narcissus.marketplace.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar

class MakeAnOrder(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(orderList: List<CartItem>,orderUUID:String): OrderPaymentResult {
//        val scope = CoroutineScope(SupervisorJob())
//        scope.launch {
//            val paymentResult = orderRepository.payForTheOrder(orderList,orderUUID)
//        }
        val paymentResult = orderRepository.payForTheOrder(orderList,orderUUID)
        if (paymentResult.status == OrderPaymentStatus.PAID) {
            orderRepository.saveOrder(
                orderList.toOrder(
                    orderUUID,
                    paymentResult.number!!,
                    Calendar.getInstance().time, OrderStatus.Accepted,
                ),
            )
        }
        return paymentResult
    }
}
