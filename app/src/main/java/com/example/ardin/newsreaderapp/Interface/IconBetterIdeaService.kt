package com.example.ardin.newsreaderapp.Interface

import com.example.ardin.newsreaderapp.Model.IconBetterIdea
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by ardin on 27/02/18.
 */
interface IconBetterIdeaService {
    @GET
    fun getIconUrl(@Url url: String) : Call<IconBetterIdea>
}