package com.narcissus.marketplace.apiclient.di

import com.narcissus.marketplace.apiclient.Constants.BASE_URL
import com.narcissus.marketplace.apiclient.api.interceptor.ApiKeyInterceptor
import com.narcissus.marketplace.apiclient.api.interceptor.NetworkCacheInterceptor
import com.narcissus.marketplace.apiclient.api.interceptor.provideCache
import com.narcissus.marketplace.apiclient.api.service.ApiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal fun okHttpClient (cache: Cache): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(ApiKeyInterceptor())
        .addInterceptor(NetworkCacheInterceptor())
        .cache(cache)
        .build()
}

internal fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

internal fun apiService(retrofit: Retrofit): ApiService {
    return retrofit.create()
}

val apiClientModule = module {
    single { apiService(get()) }
    single { provideCache(get()) }
    single { okHttpClient(get()) }
    single { retrofit(get()) }

}

