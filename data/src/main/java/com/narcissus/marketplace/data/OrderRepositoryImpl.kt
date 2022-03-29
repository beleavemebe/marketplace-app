package com.narcissus.marketplace.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.narcissus.marketplace.data.mapper.toCartItem
import com.narcissus.marketplace.data.model.CartItemBean
import com.narcissus.marketplace.data.model.OrderBean
import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.model.OrderItem
import com.narcissus.marketplace.domain.model.OrderStatus
import com.narcissus.marketplace.domain.repository.OrderRepository
import com.narcissus.marketplace.domain.util.ActionResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Calendar

internal class OrderRepositoryImpl(private val orderRef: DatabaseReference) : OrderRepository {
    override fun getOrders(): Flow<List<Order>> = callbackFlow {
        val eventListener = createValueEventListener()
        orderRef.addValueEventListener(eventListener)
        awaitClose {
            orderRef.removeEventListener(eventListener)
        }
    }

    private fun ProducerScope<List<Order>>.createValueEventListener() =
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ordersBean = snapshot.children.mapNotNull { child ->
                    child.getValue<OrderBean>()
                }
                val orders = ordersBean.map {
                    Order(
                        it.id!!, it.date!!, it.status!!,
                        it.items!!.map {
                            OrderItem(
                                it.productId!!,
                                it.productImage!!,
                                it.productPrice!!,
                                it.productName!!,
                                it.amount!!,
                            )
                        },
                    )
                }
                trySendBlocking(orders)
            }

            override fun onCancelled(error: DatabaseError) {}
        }


    override suspend fun makeAnOrder(order: Order): ActionResult<Boolean> {
        TODO("Not yet implemented")
    }
}
