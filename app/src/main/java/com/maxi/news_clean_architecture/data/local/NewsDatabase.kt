package com.maxi.news_clean_architecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxi.news_clean_architecture.data.local.dao.NewsDao
import com.maxi.news_clean_architecture.data.local.model.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}