package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.remote.OrderRepository
import com.narcissus.marketplace.repository.remote.UserRemoteRepository

class GetOrderList(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getOrders()
}