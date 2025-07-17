package com.maxi.news_clean_architecture.di.module

import com.maxi.news_clean_architecture.BuildConfig
import com.maxi.news_clean_architecture.data.remote.ApiConstants.BASE_URL
import com.maxi.news_clean_architecture.data.remote.ApiConstants.Headers.USER_AGENT
import com.maxi.news_clean_architecture.data.remote.api.NetworkService
import com.maxi.news_clean_architecture.data.remote.interceptor.AuthorizationInterceptor
import com.maxi.news_clean_architecture.data.remote.interceptor.ErrorHandlingInterceptor
import com.maxi.news_clean_architecture.di.BaseUrl
import com.maxi.news_clean_architecture.utils.DefaultDispatcherProvider
import com.maxi.news_clean_architecture.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String =
        BASE_URL

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor =
        AuthorizationInterceptor(BuildConfig.API_KEY, USER_AGENT)

    @Provides
    @Singleton
    fun provideErrorHandlingInterceptor(): ErrorHandlingInterceptor =
        ErrorHandlingInterceptor()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        errorHandlingInterceptor: ErrorHandlingInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(errorHandlingInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        httpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    fun provideNetworkService(
        retrofit: Retrofit
    ): NetworkService =
        retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()

}