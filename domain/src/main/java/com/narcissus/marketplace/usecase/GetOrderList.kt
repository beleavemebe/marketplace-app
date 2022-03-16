package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.OrderRepository

class GetOrderList(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getOrders()
}
