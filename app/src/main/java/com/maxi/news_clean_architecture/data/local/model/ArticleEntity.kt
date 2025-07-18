package com.maxi.news_clean_architecture.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxi.news_clean_architecture.data.local.RoomConstants.COLUMNS.AUTHOR
import com.maxi.news_clean_architecture.data.local.RoomConstants.COLUMNS.DESCRIPTION
import com.maxi.news_clean_architecture.data.local.RoomConstants.COLUMNS.IMAGE_URL
import com.maxi.news_clean_architecture.data.local.RoomConstants.COLUMNS.TITLE
import com.maxi.news_clean_architecture.data.local.RoomConstants.COLUMNS.URL
import com.maxi.news_clean_architecture.data.local.RoomConstants.TABLE.ARTICLES
import com.maxi.news_clean_architecture.domain.model.Article

@Entity(tableName = ARTICLES)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(URL)
    val url: String,
    @ColumnInfo(AUTHOR)
    val author: String,
    @ColumnInfo(TITLE)
    val title: String,
    @ColumnInfo(DESCRIPTION)
    val description: String,
    @ColumnInfo(IMAGE_URL)
    val imageUrl: String
)

fun ArticleEntity.toDomain(): Article =
    Article(author, title, description, url, imageUrl)
