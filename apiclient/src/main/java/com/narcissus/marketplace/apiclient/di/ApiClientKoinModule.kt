package com.narcissus.marketplace.apiclient.di

import com.narcissus.marketplace.apiclient.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val retrofit: Retrofit by lazy {
    Retrofit.Builder().build()
}

internal val apiService by lazy {
    retrofit.create<ApiService>()
}

val apiClientModule = module {
    single { apiService }
}
