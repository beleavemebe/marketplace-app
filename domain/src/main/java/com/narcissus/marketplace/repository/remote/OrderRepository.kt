package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.Order

interface OrderRepository {
    suspend fun getOrders():Result<List<Order>>
    suspend fun makeAnOrder(order:Order):Result<Boolean>
}