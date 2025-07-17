package com.maxi.news_clean_architecture.data.remote.interceptor

import com.maxi.news_clean_architecture.data.remote.ApiConstants.Headers.API_KEY
import com.maxi.news_clean_architecture.data.remote.ApiConstants.Headers.USER_AGENT_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String,
    private val userAgent: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader(API_KEY, apiKey)
                .addHeader(USER_AGENT_KEY, userAgent)
                .build()
        )
    }
}