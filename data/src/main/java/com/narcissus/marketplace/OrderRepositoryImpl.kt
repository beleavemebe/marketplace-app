package com.narcissus.marketplace

import com.narcissus.marketplace.model.Order
import com.narcissus.marketplace.repository.remote.OrderRepository
import com.narcissus.marketplace.util.ActionResult

class OrderRepositoryImpl : OrderRepository {
    override suspend fun getOrders(): ActionResult<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun makeAnOrder(order: Order): ActionResult<Boolean> {
        TODO("Not yet implemented")
    }
}
