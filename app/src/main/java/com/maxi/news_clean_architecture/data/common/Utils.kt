package com.maxi.news_clean_architecture.data.common

import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        val response = apiCall()
        Result.Success(response)
    } catch (e: ApiException) {
        Result.ApiError(e.errorCode, e.message)
    } catch (e: IOException) {
        Result.NetworkError
    } catch (e: Exception) {
        Result.UnknownError
    }
}