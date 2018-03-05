package com.example.ardin.newsreaderapp.Extension

import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


fun ImageView.loadUrl(url: String) {
    if (url != null) {
        Picasso.with(context)
                .load(url)
                .into(this)
    }
}

fun TextView.wrapTitleIfTooLong(title: String) {
    if (title != null) {
        if (title.length > 65) {
            this.text = "${title.substring(0, 65)}...."
        } else {
            this.text = "$title"
        }
    }
}