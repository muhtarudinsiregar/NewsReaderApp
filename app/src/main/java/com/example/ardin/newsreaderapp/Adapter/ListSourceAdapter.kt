package com.example.ardin.newsreaderapp.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ardin.newsreaderapp.Common.Common
import com.example.ardin.newsreaderapp.Extension.loadUrl
import com.example.ardin.newsreaderapp.Interface.ItemClickListener
import com.example.ardin.newsreaderapp.ListNews
import com.example.ardin.newsreaderapp.Model.Icon
import com.example.ardin.newsreaderapp.Model.IconBetterIdea
import com.example.ardin.newsreaderapp.Model.Source
import com.example.ardin.newsreaderapp.Model.WebSite
import com.example.ardin.newsreaderapp.R
import kotlinx.android.synthetic.main.source_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        itemClickListener?.onClick(v, adapterPosition, false)
    }

    private var itemClickListener: ItemClickListener? = null


    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    fun bindDataIcon(icon: Icon) {
        itemView.source_image.loadUrl(icon.url)
    }

    fun bindDataWebsite(source: Source) {
        itemView.source_name.text = source.name
    }
}

class ListSourceAdapter(val context: Context, val webSite: WebSite) : RecyclerView.Adapter<ListSourceViewHolder>() {

    private val newsService = Common.getIconService();

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val itemView: View = inflater.inflate(R.layout.source_layout, parent, false)

        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return webSite.sources.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        //bind data sources
        holder.bindDataWebsite(webSite.sources[position])

        //call api to get icon image url
        val iconBetterAPI = StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=")
        iconBetterAPI.append(webSite.sources.get(position).url)

        newsService.getIconUrl(iconBetterAPI.toString()).enqueue(object : Callback<IconBetterIdea> {
            override fun onFailure(call: Call<IconBetterIdea>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<IconBetterIdea>, response: Response<IconBetterIdea>) {
                val result = response.body()

                if (result?.icons != null && result?.icons.isNotEmpty()) {
                    holder.bindDataIcon(result.icons[0])
                }
            }

        })

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                val intent = Intent(context, ListNews::class.java)
                intent.apply {
                    val sortBy = webSite.sources[position].sortByAvailable

                    if (sortBy != null) intent.putExtra("sortBy", sortBy[0])

                    intent.putExtra("source", webSite.sources[position].id)
                }

                context.startActivity(intent)
            }
        })
    }

}