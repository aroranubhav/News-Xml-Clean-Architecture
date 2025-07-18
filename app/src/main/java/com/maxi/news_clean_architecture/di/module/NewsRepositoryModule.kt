package com.maxi.news_clean_architecture.di.module

import com.maxi.news_clean_architecture.data.remote.api.NetworkService
import com.maxi.news_clean_architecture.data.repository.DefaultNewsRepository
import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import com.maxi.news_clean_architecture.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsRepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        networkService: NetworkService,
        dispatcherProvider: DispatcherProvider
    ): NewsRepository = DefaultNewsRepository(
        networkService,
        dispatcherProvider
    )
}