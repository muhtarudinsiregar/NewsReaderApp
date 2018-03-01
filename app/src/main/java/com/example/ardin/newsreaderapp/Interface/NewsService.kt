package com.example.ardin.newsreaderapp.Interface

import com.example.ardin.newsreaderapp.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("v1/sources?language=en")
    fun getSources() : Call<WebSite>
}