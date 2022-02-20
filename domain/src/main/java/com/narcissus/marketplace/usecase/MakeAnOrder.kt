package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.Order
import com.narcissus.marketplace.repository.remote.OrderRepository

class MakeAnOrder(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(order:Order) =
        orderRepository.makeAnOrder(order)
}