package com.maxi.news_clean_architecture.data.common

import java.io.IOException

data class ApiException(val errorCode: Int, val errorBody: String?) :
    IOException("HTTP $errorCode")
