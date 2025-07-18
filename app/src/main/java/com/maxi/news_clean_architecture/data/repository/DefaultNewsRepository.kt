package com.maxi.news_clean_architecture.data.repository

import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.data.common.safeApiCall
import com.maxi.news_clean_architecture.data.local.dao.NewsDao
import com.maxi.news_clean_architecture.data.local.model.toDomain
import com.maxi.news_clean_architecture.data.remote.api.NetworkService
import com.maxi.news_clean_architecture.data.remote.model.toDomain
import com.maxi.news_clean_architecture.data.remote.model.toEntity
import com.maxi.news_clean_architecture.domain.model.Article
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
                val dto = response.data
                val entities = dto.map { it.toEntity() }

                safeApiCall {
                    newsDao.insertArticles(entities)
                }

                val cached = safeApiCall {
                    newsDao.getArticles().first()
                }

                if (cached is Result.Success) {
                    emit(Result.Success(cached.data.map { it.toDomain() }))
                } else {
                    emit(Result.Success(dto.map { it.toDomain() }))
                }
            }

            is Result.ApiError,
            is Result.NetworkError,
            is Result.UnknownError -> {
                val cached = safeApiCall {
                    newsDao.getArticles().first()
                }

                if (cached is Result.Success) {
                    emit(Result.Success(cached.data.map { it.toDomain() }))
                } else {
                    emit(Result.UnknownError)
                }
            }

            is Result.Loading -> {
                emit(Result.Loading)
            }

            else -> {
                error("Unhandled response type!")
            }
        }
    }.flowOn(dispatcherProvider.io)
}