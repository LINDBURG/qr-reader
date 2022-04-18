package com.linbug.qrreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class UrlListAdapter : ListAdapter<Url, UrlListAdapter.UrlViewHolder>(UrlsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        return UrlViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.url)
    }

    class UrlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val urlItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            urlItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): UrlViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return UrlViewHolder(view)
            }
        }
    }

    class UrlsComparator : DiffUtil.ItemCallback<Url>() {
        override fun areItemsTheSame(oldItem: Url, newItem: Url): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Url, newItem: Url): Boolean {
            return oldItem.url == newItem.url
        }
    }
}