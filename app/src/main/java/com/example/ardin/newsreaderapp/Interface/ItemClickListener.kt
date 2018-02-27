package com.example.ardin.newsreaderapp.Interface

import android.view.View

/**
 * Created by ardin on 27/02/18.
 */
interface ItemClickListener {
    fun onClick(view: View?, position: Int, isLongClick: Boolean) {}
}