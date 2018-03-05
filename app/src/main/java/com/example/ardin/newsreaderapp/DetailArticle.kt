package com.example.ardin.newsreaderapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_detail_article.*

class DetailArticle : AppCompatActivity() {

    private lateinit var dialog: SpotsDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        dialog = SpotsDialog(this)
        dialog.show()

        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.dismiss()
            }
        }

//        Log.d("DetailArticle", intent.getStringExtra("webURL").isEmpty().toString())
        if (intent != null) {
            if (!intent.getStringExtra("webURL").isEmpty()) {
                webView.loadUrl(intent.getStringExtra("webURL"))
            }
        }

    }
}
