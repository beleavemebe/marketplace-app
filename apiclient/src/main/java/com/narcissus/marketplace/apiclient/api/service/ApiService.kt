package com.narcissus.marketplace.apiclient.api.service

import com.narcissus.marketplace.apiclient.api.model.ProductDetailsResponse
import com.narcissus.marketplace.apiclient.api.model.ProductPreviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Если лимит дефлолтный, вынесем в interceptor
    @GET("products/{id}")
    suspend fun getProductDetails(
        @Path("id") productId: String,
    ): ProductDetailsResponse

    @GET("products/random")
    suspend fun getRandomProducts(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): ProductPreviewsResponse

    @GET("products/toprated")
    suspend fun getTopRatedProducts(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): ProductPreviewsResponse

    @GET("products/topsales")
    suspend fun getTopSalesProducts(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): ProductPreviewsResponse
}
