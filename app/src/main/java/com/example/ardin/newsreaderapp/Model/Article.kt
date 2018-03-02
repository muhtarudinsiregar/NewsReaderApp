package com.example.ardin.newsreaderapp.Model

/**
 * Created by ardin on 02/03/18.
 */
data class Article(
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String
)