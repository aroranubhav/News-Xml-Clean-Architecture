package com.maxi.news_clean_architecture.data.remote.model

import com.google.gson.annotations.SerializedName
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.ARTICLES
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.STATUS
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.TOTAL_RESULTS

data class NewsResponse(
    @SerializedName(STATUS)
    val status: String = "",
    @SerializedName(TOTAL_RESULTS)
    val totalResults: Int = 0,
    @SerializedName(ARTICLES)
    val articles: List<ArticleDto> = arrayListOf()
)
