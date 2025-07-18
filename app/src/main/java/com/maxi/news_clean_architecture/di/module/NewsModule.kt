package com.maxi.news_clean_architecture.di.module

import com.maxi.news_clean_architecture.presentation.news.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class NewsModule {

    @Provides
    @ActivityScoped
    fun provideNewsAdapter(): NewsAdapter =
        NewsAdapter(arrayListOf())
}