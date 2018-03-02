package com.example.ardin.newsreaderapp.Common

import com.example.ardin.newsreaderapp.Interface.IconBetterIdeaService
import com.example.ardin.newsreaderapp.Interface.NewsService
import com.example.ardin.newsreaderapp.Remote.IconBetterIdeaClient
import com.example.ardin.newsreaderapp.Remote.RetrofitClient

/**
 * Created by ardin on 27/02/18.
 */
object Common {
    fun getNewsService(): NewsService {
        return RetrofitClient.getClient(API.BASE_URL).create(NewsService::class.java)
    }

    fun getIconService(): IconBetterIdeaService {
        return IconBetterIdeaClient
                .getClient(API.BASE_URL).create(IconBetterIdeaService::class.java)
    }

    fun getAPIUrl(source: String, sortBy: String?, apiKEY: String): String {
        val apiUrl = StringBuilder("${API.BASE_URL}/v1/articles?source=")
        return apiUrl.append(source)
                .append("&sortBy")
                .append(sortBy)
                .append("&apiKey=")
                .append(apiKEY)
                .toString()
    }
}