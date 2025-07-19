package com.maxi.news_clean_architecture.domain.usecase

import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.domain.model.Article
import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) : NewsUseCase {
    override fun invoke(): Flow<Result<List<Article>>> =
        repository.getNews()
}