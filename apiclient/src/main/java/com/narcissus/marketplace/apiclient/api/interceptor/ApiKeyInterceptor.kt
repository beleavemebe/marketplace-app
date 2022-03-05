package com.narcissus.marketplace.apiclient.api.interceptor

import com.narcissus.marketplace.apiclient.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val url =
            originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", BuildConfig.DUMMYPRODUCTSAPIKEY).build()
        request.url(url)
        return chain.proceed(request.build())
    }
}
