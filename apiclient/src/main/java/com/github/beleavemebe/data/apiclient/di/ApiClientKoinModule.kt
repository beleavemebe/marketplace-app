package com.github.beleavemebe.data.apiclient.di

import com.github.beleavemebe.data.apiclient.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val retrofit: Retrofit =
    Retrofit.Builder()
        .build()

internal val apiService = retrofit
    .create<ApiService>()

val apiClientModule = module {
    single { apiService }
}
