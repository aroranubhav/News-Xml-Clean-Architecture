package com.maxi.news_clean_architecture.domain.model

data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String
)
