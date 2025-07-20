package com.maxi.news_clean_architecture.di.module

import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import com.maxi.news_clean_architecture.domain.usecase.DefaultNewsUseCase
import com.maxi.news_clean_architecture.domain.usecase.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideNewsUseCase(
        repository: NewsRepository
    ): NewsUseCase =
        DefaultNewsUseCase(repository)
}