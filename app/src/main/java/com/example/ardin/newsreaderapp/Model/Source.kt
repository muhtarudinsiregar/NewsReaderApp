package com.example.ardin.newsreaderapp.Model

data class UrlToLogos(val small: String, val medium: String, val large: String)

data class Source(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String,
        val urlToLogos: UrlToLogos,
        val sortByAvailable: List<String>
)
