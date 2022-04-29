package com.example.newsapp.home.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.model.Article


class NewsRecyclerAdapter(
    private val context: Context,
    //private val clickListener: OnClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    private var articlesList = mutableListOf<Article>()

    fun setArticlesList(articlesList: List<Article>) {
        this.articlesList = articlesList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articlesList[position]
        holder.binding.titleTv.text = article.title
        if (!article.author.isNullOrEmpty()) {
            holder.binding.textView2.text = "By: " + article.author
            Log.i("NewsRecyclerAdapter", "author " + article.author)
        } else {
            holder.binding.textView2.text = "By: " + article.source.name
            Log.i("NewsRecyclerAdapter", "sourse " + article.source.name)
        }
        Glide.with(context).load(article.urlToImage).placeholder(R.drawable.photo)
            .into(holder.binding.roundedImageView)
        //holder.binding.daysCardView.setOnClickListener { clickListener.onClick(article) }
    }

    override fun getItemCount(): Int = articlesList.size
}

class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)