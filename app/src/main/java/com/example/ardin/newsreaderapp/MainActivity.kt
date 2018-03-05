package com.example.ardin.newsreaderapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.ardin.newsreaderapp.Adapter.ListSourceAdapter
import com.example.ardin.newsreaderapp.Common.Common
import com.example.ardin.newsreaderapp.Interface.NewsService
import com.example.ardin.newsreaderapp.Model.WebSite
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var newsService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: SpotsDialog
    lateinit var cache: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh.setOnRefreshListener {
            loadWebsiteSource(true)
        }

        //init cache
        Paper.init(this)

        //init service
        newsService = Common.getNewsService()

        list_source.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        dialog = SpotsDialog(this)

        loadWebsiteSource(false)

    }

    private fun loadWebsiteSource(isRefreshed: Boolean) {
        if (!isRefreshed) {
            cache = Paper.book().read("cache")

            if (!cache.isEmpty()) { //if have cache
                val website: WebSite = Gson().fromJson(cache, WebSite::class.java)
                val adapter = ListSourceAdapter(baseContext, website)

                adapter.notifyDataSetChanged()
                list_source.adapter = adapter
            } else {
                dialog.show()

                newsService.getSources().enqueue(object : Callback<WebSite> {
                    override fun onFailure(call: Call<WebSite>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext, response.body() as WebSite)
                        adapter.notifyDataSetChanged()

                        list_source.adapter = adapter

                        //save to cache
                        Paper.book().write("cache", Gson().toJson(response.body()))
                    }
                })
            }

        } else {

            newsService.getSources().enqueue(object : Callback<WebSite> {
                override fun onFailure(call: Call<WebSite>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext, response.body() as WebSite)
                    adapter.notifyDataSetChanged()

                    list_source.adapter = adapter

                    //save to cache
                    Paper.book().write("cache", Gson().toJson(response.body()))

                    //dismiss refresh progressing
                    swipeRefresh.isRefreshing = false
                }

            })
        }
    }
}
