package com.example.compose_example

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(name = "Accept:", value = "application/json")
            .addHeader(name = "ApiKey", value = "the apikey number")
        //you can keep adding headers here.
            .build()
        return chain.proceed(request)
    }
}