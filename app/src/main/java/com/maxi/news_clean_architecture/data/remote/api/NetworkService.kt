package com.maxi.news_clean_architecture.data.remote.api

import com.maxi.news_clean_architecture.data.remote.ApiConstants.Endpoints.TOP_HEADLINES
import com.maxi.news_clean_architecture.data.remote.ApiConstants.QueryParams.COUNTRY
import com.maxi.news_clean_architecture.data.remote.ApiConstants.QueryParams.DEFAULT_COUNTRY
import com.maxi.news_clean_architecture.data.remote.ApiConstants.QueryParams.DEFAULT_LANGUAGE
import com.maxi.news_clean_architecture.data.remote.ApiConstants.QueryParams.LANGUAGE
import com.maxi.news_clean_architecture.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(TOP_HEADLINES)
    suspend fun getNews(
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(COUNTRY) country: String = DEFAULT_COUNTRY
    ): NewsResponse
}