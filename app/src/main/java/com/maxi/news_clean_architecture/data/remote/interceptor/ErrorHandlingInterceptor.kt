package com.maxi.news_clean_architecture.data.remote.interceptor

import com.maxi.news_clean_architecture.data.common.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            throw IOException("Network error: ${e.localizedMessage}", e)
        }

        if (!response.isSuccessful) {
            val errorBody = response.body?.string() //read error as string
            throw ApiException(response.code, errorBody)
        }
        return response
    }
}
