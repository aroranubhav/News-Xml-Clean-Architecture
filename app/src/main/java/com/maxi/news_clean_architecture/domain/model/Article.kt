package com.maxi.news_clean_architecture.domain.model

import com.google.gson.annotations.SerializedName
import com.maxi.news_clean_architecture.data.local.model.ArticleEntity
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.AUTHOR
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.DESCRIPTION
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.IMAGE_URL
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.TITLE
import com.maxi.news_clean_architecture.data.remote.ApiConstants.JsonKeys.URL

data class Article(
    @SerializedName(AUTHOR)
    val author: String? = "",
    @SerializedName(TITLE)
    val title: String? = "",
    @SerializedName(DESCRIPTION)
    val description: String? = "",
    @SerializedName(URL)
    val url: String = "",
    @SerializedName(IMAGE_URL)
    val imageUrl: String? = ""
)

fun Article.toEntity(): ArticleEntity =
    ArticleEntity(
        url,
        author ?: "",
        title ?: "",
        description ?: "",
        imageUrl ?: ""
    )
