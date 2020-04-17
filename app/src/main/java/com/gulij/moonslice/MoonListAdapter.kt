package com.gulij.moonslice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoonListAdapter(private val dates: Array<String>) :
    RecyclerView.Adapter<MoonListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_noon,
            parent,
            false
        ) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dates[position]
    }

    override fun getItemCount() = dates.size

    class ViewHolder(var textView: TextView) : RecyclerView.ViewHolder(textView)
}