package com.narcissus.marketplace.apiclient.api.model

import com.google.gson.annotations.SerializedName

data class OrderPaymentResponse(
    @SerializedName("order_id")
    val orderId:String,
    @SerializedName("order_number")
    val orderNumber: Int?,
    @SerializedName("order_payment_status")
    val orderPaymentStatus: String,
    @SerializedName("message")
    val message: String,
)

data class OrderPaymentQueryBody(
    @SerializedName("id")
    val id:String,
    @SerializedName("order_items")
    val orderedItemsList: List<OrderPaymentQueryBodyItem>,
)

data class OrderPaymentQueryBodyItem(
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_amount")
    val productAmount: Int,
)

