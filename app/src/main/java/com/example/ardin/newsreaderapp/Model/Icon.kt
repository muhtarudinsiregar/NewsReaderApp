package com.example.ardin.newsreaderapp.Model

/**
 * Created by ardin on 27/02/18.
 */
data class Icon(
        val url: String,
        val width: Int,
        val height: Int,
        val bytes: Int,
        val format: String,
        val sha1sum: String,
        val error: Any
)