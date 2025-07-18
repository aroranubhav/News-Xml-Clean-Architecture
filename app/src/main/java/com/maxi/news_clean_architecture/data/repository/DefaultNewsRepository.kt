package com.maxi.news_clean_architecture.data.repository

import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.data.common.safeApiCall
import com.maxi.news_clean_architecture.data.local.dao.NewsDao
import com.maxi.news_clean_architecture.data.local.model.toDomain
import com.maxi.news_clean_architecture.data.remote.api.NetworkService
import com.maxi.news_clean_architecture.domain.model.Article
import com.maxi.news_clean_architecture.domain.model.toEntity
import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import com.maxi.news_clean_architecture.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val newsDao: NewsDao,
    private val dispatcherProvider: DispatcherProvider
) : NewsRepository {

    override fun getNews(): Flow<Result<List<Article>>> = flow {
        val response = safeApiCall {
            networkService.getNews().articles
        }

        when (response) {
            is Result.Success -> {
                safeApiCall {
                    newsDao.insertArticles(response.data.map { it.toEntity() })
                }
                emit(Result.Success(response.data))
            }

            is Result.ApiError,
            is Result.NetworkError,
            is Result.UnknownError -> {
                val cachedArticles = safeApiCall {
                    newsDao.getArticles().first()
                }

                if (cachedArticles is Result.Success) {
                    emit(Result.Success(cachedArticles.data.map { it.toDomain() }))
                } else {
                    emit(response)
                }
            }

            is Result.Loading -> {
                emit(Result.Loading)
            }

            else -> {
                emit(response)
            }
        }
    }.flowOn(dispatcherProvider.io)
}