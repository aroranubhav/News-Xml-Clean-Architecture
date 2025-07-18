package com.maxi.news_clean_architecture.data.repository

import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.data.common.safeApiCall
import com.maxi.news_clean_architecture.data.remote.api.NetworkService
import com.maxi.news_clean_architecture.domain.model.Article
import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import com.maxi.news_clean_architecture.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val dispatcherProvider: DispatcherProvider
) : NewsRepository {

    override fun getNews(): Flow<Result<List<Article>>> = flow {
        val response = safeApiCall {
            networkService.getNews().articles
        }

        when (response) {
            is Result.Success -> {
                emit(response)
            }

            is Result.ApiError -> {
                emit(Result.ApiError(response.code, response.message))
            }

            is Result.NetworkError -> {
                emit(Result.NetworkError)
            }

            is Result.UnknownError -> {
                emit(Result.UnknownError)
            }

            else -> {
                emit(Result.Loading)
            }
        }
    }.flowOn(dispatcherProvider.io)
}