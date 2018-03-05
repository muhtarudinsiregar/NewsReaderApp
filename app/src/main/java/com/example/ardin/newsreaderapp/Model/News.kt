package com.example.ardin.newsreaderapp.Model

/**
 * Created by ardin on 02/03/18.
 */
data class News(
        val status: String,
        val source: String,
        val sortBy: String,
        val articles: List<Article>
)