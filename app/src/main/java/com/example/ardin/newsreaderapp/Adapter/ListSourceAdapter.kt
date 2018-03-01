package com.example.ardin.newsreaderapp.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ardin.newsreaderapp.Common.Common
import com.example.ardin.newsreaderapp.Interface.ItemClickListener
import com.example.ardin.newsreaderapp.Model.IconBetterIdea
import com.example.ardin.newsreaderapp.Model.WebSite
import com.example.ardin.newsreaderapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.source_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ardin on 27/02/18.
 */

class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    override fun onClick(v: View?) {
        itemClickListener?.onClick(v, adapterPosition, false)
    }

    private var itemClickListener: ItemClickListener? = null


    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun bindData(url: String) {
//        itemView.source_image.setText(icon.icons.get(0).url)
        Picasso.with(itemView.context).load(url).into(itemView.source_image)
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
        val iconBetterAPI = StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=")
        iconBetterAPI.append(webSite.sources.get(position).url)

        newsService.getIconUrl(iconBetterAPI.toString()).enqueue(object : Callback<IconBetterIdea> {
            override fun onFailure(call: Call<IconBetterIdea>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<IconBetterIdea>, response: Response<IconBetterIdea>) {
                val result = response.body()

                if (result?.icons != null && result?.icons.isNotEmpty()) {
                    holder.bindData(result.icons[0].url)
                }

            }

        })

        holder?.itemView.source_name.setText(webSite.sources.get(position).url)

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                super.onClick(view, position, isLongClick)
            }
        })
    }

}