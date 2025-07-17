package com.maxi.news_clean_architecture.data.remote

object ApiConstants {

    const val BASE_URL = "https://newsapi.org/v2/"

    object Endpoints {

        const val TOP_HEADLINES = "top-headlines"
    }

    object Headers {
        const val API_KEY = "X-Api-Key"
        const val USER_AGENT_KEY = "User-Agent"
        const val USER_AGENT = "x-maxi-droid"

    }

    object QueryParams {

        const val LANGUAGE = "language"
        const val COUNTRY = "country"
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_COUNTRY = "us"
    }

    object JsonKeys {

        const val STATUS = "status"
        const val TOTAL_RESULTS = "totalResults"
        const val ARTICLES = "articles"
        const val AUTHOR = "author"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URL = "url"
        const val IMAGE_URL = "urlToImage"
    }
}