package com.narcissus.marketplace.apiclient.api.interceptor

import android.content.Context
import com.narcissus.marketplace.apiclient.Constants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.File
import java.util.concurrent.TimeUnit

class NetworkCacheInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(Constants.CACHE_MAX_AGE, TimeUnit.MINUTES)
            .build()

        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
        return response
    }
}

fun provideCache(context: Context): Cache {
    val cacheSize = Constants.CACHE_SIZE
    val httpCacheDirectory = File(context.cacheDir, Constants.CACHE_DIR)
    return Cache(httpCacheDirectory, cacheSize.toLong())
}
