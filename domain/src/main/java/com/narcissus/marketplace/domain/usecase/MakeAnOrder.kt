package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.repository.OrderRepository

class MakeAnOrder(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(order: Order) = orderRepository.makeAnOrder(order)
}
