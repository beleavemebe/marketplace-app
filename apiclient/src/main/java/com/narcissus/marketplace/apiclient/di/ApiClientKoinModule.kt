package com.narcissus.marketplace.apiclient.di

import com.narcissus.marketplace.apiclient.BuildConfig
import com.narcissus.marketplace.apiclient.api.interceptor.ApiKeyInterceptor
import com.narcissus.marketplace.apiclient.api.service.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val okHttpClient by lazy {
    OkHttpClient().newBuilder()
        .addInterceptor(ApiKeyInterceptor())
        .build()
}

internal val retrofit: Retrofit by lazy {
// base url is https://dummyproducts-api.herokuapp.com/api/v1/
// must be added in local.properties project level
    Retrofit.Builder().baseUrl(BuildConfig.DUMMYPRODUCTSBASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

internal val apiService by lazy {
    retrofit.create<ApiService>()
}

val apiClientModule = module {
    single { apiService }
}
