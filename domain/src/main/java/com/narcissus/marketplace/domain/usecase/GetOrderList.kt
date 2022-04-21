package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.repository.OrderRepository

class GetOrderList(private val orderRepository: OrderRepository) {
    operator fun invoke() = orderRepository.getOrders()
}
