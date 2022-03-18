package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.util.ActionResult

interface OrderRepository {
    suspend fun getOrders(): ActionResult<List<Order>>
    suspend fun makeAnOrder(order: Order): ActionResult<Boolean>
}