package com.narcissus.marketplace.apiclient.di

import com.narcissus.marketplace.apiclient.api.ApiService
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val retrofit: Retrofit by lazy {
    Retrofit.Builder().build()
}

internal val apiService by lazy {
    retrofit.create<ApiService>()
}

internal lateinit var apiKey: String

fun apiClientModule(_apiKey: String): Module {
    apiKey = _apiKey
    return module {
        single { apiService }
    }
}
