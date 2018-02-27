package com.example.ardin.newsreaderapp.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.ardin.newsreaderapp.Interface.ItemClickListener

/**
 * Created by ardin on 27/02/18.
 */

class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    override fun onClick(v: View?) {
        itemClickListener?.onClick(v, adapterPosition, false)
    }

    private var itemClickListener: ItemClickListener? = null



    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}

class ListSourceAdapter {

}