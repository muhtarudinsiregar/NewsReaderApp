package com.example.ardin.newsreaderapp.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ardin on 27/02/18.
 */
object IconBetterIdeaClient {
    fun getClient(baseUrl: String): Retrofit {
        var retrofit: Retrofit? = null

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

        return retrofit
    }
}