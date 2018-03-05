package com.example.ardin.newsreaderapp.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ardin.newsreaderapp.Common.ISO8601Parse
import com.example.ardin.newsreaderapp.Extension.loadUrl
import com.example.ardin.newsreaderapp.Extension.wrapTitleIfTooLong
import com.example.ardin.newsreaderapp.Interface.ItemClickListener
import com.example.ardin.newsreaderapp.Model.Article
import com.example.ardin.newsreaderapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_layout.view.*
import java.text.ParseException
import java.util.*


class ListNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
    lateinit var itemClickListener: ItemClickListener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v, adapterPosition, false)
    }

    fun bindData(article: Article) {
        //set data using extension function kotlin
        itemView.article_image.loadUrl(article.urlToImage)
        itemView.article_title.wrapTitleIfTooLong(article.title)

        var date: Date? = null
        try {
            date = ISO8601Parse.parse(article.publishedAt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        itemView.article_time.text = date?.time.toString()

        itemView.setOnClickListener {
            object : ItemClickListener {

            }
        }

    }
}

class ListNewsAdapter(val context: Context, val articleList: List<Article>) : RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)

        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder?, position: Int) {
        holder?.bindData(articleList[position])
    }

}