package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.Order
import com.narcissus.marketplace.util.ActionResult

interface OrderRepository {
    suspend fun getOrders(): ActionResult<List<Order>>
    suspend fun makeAnOrder(order: Order): ActionResult<Boolean>
}