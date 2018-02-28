package com.example.ardin.newsreaderapp.Common

import com.example.ardin.newsreaderapp.Interface.IconBetterIdeaService
import com.example.ardin.newsreaderapp.Interface.NewsService
import com.example.ardin.newsreaderapp.Remote.IconBetterIdeaClient
import com.example.ardin.newsreaderapp.Remote.RetrofitClient

/**
 * Created by ardin on 27/02/18.
 */
object Common {
    const val BASE_URL = "https://newsapi.org"

    const val API_KEY = "2f49b9f8b3fc474888e9f02575e4cdd6"

    fun getNewsService(): NewsService {
        return RetrofitClient.getClient(BASE_URL)!!.create(NewsService::class.java)
    }

    fun getIconService(): IconBetterIdeaService {
        return IconBetterIdeaClient
                .getClient(BASE_URL)!!.create(IconBetterIdeaService::class.java)
    }
}