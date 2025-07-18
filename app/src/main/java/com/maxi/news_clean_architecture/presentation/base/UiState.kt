package com.maxi.news_clean_architecture.presentation.base

sealed class UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>()

    data class ApiError(val code: Int, val message: String?) : UiState<Nothing>()

    data object NetworkError : UiState<Nothing>()

    data object UnknownError : UiState<Nothing>()

    data object Loading : UiState<Nothing>()
}