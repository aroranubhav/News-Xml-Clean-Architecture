package com.maxi.news_clean_architecture.presentation.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.domain.model.Article
import com.maxi.news_clean_architecture.domain.usecase.NewsUseCase
import com.maxi.news_clean_architecture.presentation.base.UiState
import com.maxi.news_clean_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val useCase: NewsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            useCase()
                .flowOn(dispatcherProvider.io)
                .collect { result ->
                    when (result) {
                        is Result.Success ->
                            _uiState.value = UiState.Success(result.data)

                        is Result.ApiError ->
                            _uiState.value = UiState.ApiError(result.code, result.message)

                        is Result.DatabaseError ->
                            _uiState.value = UiState.DatabaseError(result.error)

                        is Result.NetworkError ->
                            _uiState.value = UiState.NetworkError

                        is Result.UnknownError ->
                            _uiState.value = UiState.UnknownError

                        is Result.Loading ->
                            _uiState.value = UiState.Loading
                    }
                }
        }
    }
}