package com.maxi.news_clean_architecture.data.common

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class ApiError(val code: Int, val message: String?) : Result<Nothing>()

    data object NetworkError : Result<Nothing>()

    data object UnknownError : Result<Nothing>()

    data object Loading : Result<Nothing>()
}