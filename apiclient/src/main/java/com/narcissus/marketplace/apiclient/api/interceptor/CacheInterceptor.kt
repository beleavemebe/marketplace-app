package com.narcissus.marketplace.apiclient.api.interceptor

import com.narcissus.marketplace.apiclient.Constants
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
            .maxAge(Constants.CACHE_MAX_AGE, TimeUnit.MINUTES)
            .build()

        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}
