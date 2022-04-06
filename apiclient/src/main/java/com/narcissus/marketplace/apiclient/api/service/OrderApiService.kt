package com.narcissus.marketplace.apiclient.api.service

import com.narcissus.marketplace.apiclient.api.model.OrderPaymentQueryBody
import com.narcissus.marketplace.apiclient.api.model.OrderPaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiService {
    @POST("actions/checkout")
    suspend fun payForTheOrder(
        @Body body: OrderPaymentQueryBody
    ): OrderPaymentResponse
}
