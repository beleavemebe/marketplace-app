package com.narcissus.marketplace.apiclient.di

import com.narcissus.marketplace.apiclient.Constants
import com.narcissus.marketplace.apiclient.Constants.BASE_URL
import com.narcissus.marketplace.apiclient.api.interceptor.CacheInterceptor
import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.apiclient.api.service.OrderApiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

val apiClientModule = module {
    single {
        val cacheDirectory = File(androidContext().cacheDir, Constants.CACHE_DIR)
        Cache(cacheDirectory, Constants.CACHE_SIZE)
    }

    single {
        OkHttpClient().newBuilder()
            .addInterceptor(CacheInterceptor())
            .cache(get())
            .build()
    }

    single(qualifier<Qualifiers.ContentApiService>()) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single(qualifier<Qualifiers.OrderApiService>()) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>(qualifier<Qualifiers.ContentApiService>()).create<ApiService>()
    }
    single {
        get<Retrofit>(qualifier<Qualifiers.OrderApiService>()).create<OrderApiService>()
    }
}
