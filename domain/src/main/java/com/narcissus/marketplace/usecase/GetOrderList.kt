package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.OrderRepository

class GetOrderList(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getOrders()
}
