package com.example.ardin.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.ardin.newsreaderapp.Common.API
import com.example.ardin.newsreaderapp.Common.Common
import com.example.ardin.newsreaderapp.Interface.NewsService
import com.example.ardin.newsreaderapp.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {
    private var source: String? = ""
    private var sortBy: String? = ""

    private lateinit var dialog: SpotsDialog

    lateinit var mService: NewsService

    private var webHotUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        val mService = Common.getNewsService()
        dialog = SpotsDialog(this)

        swipeRefresh.setOnRefreshListener {
            loadNews(source as String, true)
        }

        diagonaLayout.setOnClickListener {
            val detail = Intent(baseContext, DetailArticle::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        if (intent != null) {
            source = intent.getStringExtra("source")
            sortBy = intent.getStringExtra("sortBy")

            if (sortBy != null && source != null) {
                loadNews(source as String, false)
            }

        }


    }

    private fun loadNews(source: String, isRefreshed: Boolean) {
        if (!isRefreshed) {
            dialog.show()
            mService.getNewestArticles(Common.getAPIUrl(source, sortBy, API.KEY))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>?, t: Throwable?) {
                            dialog.dismiss()
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()
                            val result = response?.body()
                            Log.d("ListNews", result.toString())
                            Picasso.with(baseContext)
                                    .load(result?.articles?.get(0)?.urlToImage)

                            top_title.text = result?.articles?.get(0)?.title
                            top_author.text = result?.articles?.get(0)?.author

                            webHotUrl = result?.articles?.get(0)?.url
                        }

                    })
        } else {

        }
    }
}
