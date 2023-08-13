package com.wisnu.salttechnicaltest.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisnu.salttechnicaltest.R
import com.wisnu.salttechnicaltest.databinding.ContentNewsBinding
import com.wisnu.salttechnicaltest.models.Article
import com.wisnu.salttechnicaltest.utils.simpleFormattedDate
import java.text.SimpleDateFormat
import java.util.Date

class NewsAdapter(
    private val callback: ((Article) -> Unit)?
) : PagingDataAdapter<Article, NewsAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = ContentNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                callback?.invoke(data)
            }
        }
    }

    class MainViewHolder(private val binding: ContentNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {
                try {
                    Glide.with(itemView.context)
                        .load(item.urlToImage)
                        .error(R.drawable.baseline_newspaper_24)
                        .into(viewLeft)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.d("wisnu siapa nich","asdasda $item")
                tvTitle.text = item.title
                tvSubTitle.text = item.publishedAt.simpleFormattedDate()
                tvDesc.text = item.authorAndSource()
            }
        }
    }
}

