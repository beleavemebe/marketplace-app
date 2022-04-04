package com.narcissus.marketplace.apiclient.api.service

import com.narcissus.marketplace.apiclient.api.model.OrderPaymentQueryBody
import com.narcissus.marketplace.apiclient.api.model.OrderPaymentResponse
import com.narcissus.marketplace.apiclient.api.model.ProductPreviewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApiService {
    @POST("actions/checkout")
    suspend fun payForTheOrder(
        @Body body:OrderPaymentQueryBody
    ): OrderPaymentResponse
}
