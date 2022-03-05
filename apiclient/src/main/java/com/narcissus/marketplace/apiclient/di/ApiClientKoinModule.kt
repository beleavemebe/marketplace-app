package com.narcissus.marketplace.apiclient.di

import com.narcissus.marketplace.apiclient.BuildConfig
import com.narcissus.marketplace.apiclient.api.interceptor.ApiKeyInterceptor
import com.narcissus.marketplace.apiclient.api.service.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val retrofit: Retrofit by lazy {
    Retrofit.Builder().baseUrl(BuildConfig.DUMMYPRODUCTSBASEURL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .client(OkHttpClient().newBuilder().addInterceptor(ApiKeyInterceptor()).build()).build()
}

internal val apiService by lazy {
    retrofit.create<ApiService>()
}

val apiClientModule = module {
    single { apiService }
}

