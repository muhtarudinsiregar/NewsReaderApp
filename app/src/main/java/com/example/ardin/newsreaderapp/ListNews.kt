package com.example.ardin.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.ardin.newsreaderapp.Adapter.ListNewsAdapter
import com.example.ardin.newsreaderapp.Common.API
import com.example.ardin.newsreaderapp.Common.Common
import com.example.ardin.newsreaderapp.Model.Article
import com.example.ardin.newsreaderapp.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {
    private var source: String = ""
    private var sortBy: String = "top"

    private lateinit var dialog: SpotsDialog

    private var webHotUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        dialog = SpotsDialog(this)

//        swipeRefresh.setOnRefreshListener {
//            loadNews(source as String, true)
//        }

        diagonaLayout.setOnClickListener {
            val detail = Intent(baseContext, DetailArticle::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        //show remain article

        lstNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }



        if (intent != null) {
            source = intent.getStringExtra("source")
//            sortBy = intent.getStringExtra("sortBy")

            if (source.isNotEmpty()) {
                loadNews(source, false)
            }

        }


    }

    private fun loadNews(source: String, isRefreshed: Boolean) {
        val mService = Common.getNewsService()

        if (!isRefreshed) {
            dialog.show()

            mService.getNewestArticles(Common.getAPIUrl(source, sortBy, API.KEY))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>?, t: Throwable?) {
                            dialog.dismiss()
                            Log.d("ListNews", "gagal")
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()
                            val result = response?.body()

                            Picasso.with(baseContext)
                                    .load(result?.articles?.get(0)?.urlToImage)
                                    .into(top_image)

                            top_title.text = result?.articles?.get(0)?.title
                            top_author.text = result?.articles?.get(0)?.author

                            webHotUrl = result?.articles?.get(0)?.url

                            //load remain article
                            val removeFirstArticle = removeFirstArticle(response?.body()?.articles as MutableList<Article>)

                            val adapter = ListNewsAdapter(baseContext, removeFirstArticle)
                            adapter.notifyDataSetChanged()

                            lstNews.adapter = adapter

                        }

                    })
        } else {
            dialog.show()

            //get article
            mService.getNewestArticles(Common.getAPIUrl(source, sortBy, API.KEY))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>?, t: Throwable?) {
                            dialog.dismiss()
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()

                            val result = response?.body()

                            Picasso.with(baseContext)
                                    .load(result?.articles?.get(0)?.urlToImage)
                                    .into(top_image)

                            top_title.text = result?.articles?.get(0)?.title
                            top_author.text = result?.articles?.get(0)?.author

                            webHotUrl = result?.articles?.get(0)?.url

                            //load remain article
                            val removeFirstArticle: List<Article> = removeFirstArticle(result?.articles as MutableList<Article>)

                            val adapter = ListNewsAdapter(baseContext, removeFirstArticle)
                            adapter.notifyDataSetChanged()

                            lstNews.adapter = adapter

                        }

                    })

            swipeRefresh.isRefreshing = false
        }
    }

    fun removeFirstArticle(result: MutableList<Article>): List<Article> {
        var removeFirstArticles = result
        removeFirstArticles.removeAt(0)
        return removeFirstArticles
    }
}
