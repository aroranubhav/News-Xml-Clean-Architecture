package com.maxi.news_clean_architecture.domain.repository

import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(): Flow<Result<List<Article>>>
}