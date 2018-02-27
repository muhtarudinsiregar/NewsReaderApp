package com.example.ardin.newsreaderapp.Common

import com.example.ardin.newsreaderapp.Remote.RetrofitClient
import com.example.ardin.newsreaderapp.Interface.NewsService

/**
 * Created by ardin on 27/02/18.
 */
object Common {
    const val BASE_URL = "https://newsapi.org"

    fun getNewsService(): NewsService {
        return RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)
    }
}