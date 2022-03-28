package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.util.ActionResult

interface OrderRepository {
    suspend fun getOrders(): List<Order>
    suspend fun makeAnOrder(order: Order): Boolean
}
