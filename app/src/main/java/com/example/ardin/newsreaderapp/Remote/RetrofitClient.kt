package com.example.ardin.newsreaderapp.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ardin on 27/02/18.
 */
class RetrofitClient {
    companion object {
        fun getClient(baseUrl: String): Retrofit {
            lateinit var retrofit: Retrofit
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
    }
}
