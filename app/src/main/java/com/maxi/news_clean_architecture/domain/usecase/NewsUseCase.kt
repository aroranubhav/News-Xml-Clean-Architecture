package com.maxi.news_clean_architecture.domain.usecase

import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    operator fun invoke(): Flow<Result<List<Article>>>
}