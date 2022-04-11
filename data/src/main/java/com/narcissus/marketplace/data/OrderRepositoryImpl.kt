package com.narcissus.marketplace.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.narcissus.marketplace.apiclient.api.model.OrderPaymentQueryBody
import com.narcissus.marketplace.apiclient.api.model.OrderPaymentRequestBodyItem
import com.narcissus.marketplace.apiclient.api.model.OrderPaymentResponse
import com.narcissus.marketplace.apiclient.api.service.OrderApiService
import com.narcissus.marketplace.data.mapper.orderPaymentResponseStatusToOrderPaymentStatus
import com.narcissus.marketplace.data.mapper.toOrder
import com.narcissus.marketplace.data.model.OrderBean
import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.orders.Order
import com.narcissus.marketplace.domain.model.orders.OrderPaymentResult
import com.narcissus.marketplace.domain.repository.OrderRepository
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class OrderRepositoryImpl(
    private val orderRef: DatabaseReference,
    private val orderApiService: OrderApiService,
) : OrderRepository {
    override fun getOrders(): Flow<List<Order>> = callbackFlow {
        val eventListener = createValueEventListener()
        orderRef.addValueEventListener(eventListener)
        awaitClose {
            orderRef.removeEventListener(eventListener)
        }
    }

    override suspend fun payForTheOrder(
        orderList: List<CartItem>,
        orderUUID: String,
    ): OrderPaymentResult =
        orderApiService.payForTheOrder(orderList.toOrderQueryBody(orderUUID)).toOrderPaymentResult()

    private fun ProducerScope<List<Order>>.createValueEventListener() =
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ordersBean = snapshot.children.mapNotNull { child ->
                    child.getValue<OrderBean>()?.toOrder()
                }
                trySendBlocking(ordersBean)
            }

            override fun onCancelled(error: DatabaseError) {}
        }

    override suspend fun saveOrder(order: Order) {
        orderRef.child(order.id).setValue(order)
    }

    private fun List<CartItem>.toOrderQueryBody(orderUUID: String): OrderPaymentQueryBody =
        OrderPaymentQueryBody(
            orderUUID,
            (
                this.map {
                    OrderPaymentRequestBodyItem(
                        it.productId,
                        it.amount,
                    )
                }
                ),
        )

    private fun OrderPaymentResponse.toOrderPaymentResult(): OrderPaymentResult =
        OrderPaymentResult(
            orderId,
            orderNumber,
            orderPaymentResponseStatusToOrderPaymentStatus(orderPaymentStatus),
            message,
        )

    companion object {
        const val PAYMENT_STATUS_PAID = "paid"
    }
}
