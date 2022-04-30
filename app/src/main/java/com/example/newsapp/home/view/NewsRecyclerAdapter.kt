package com.example.newsapp.home.view

import android.content.Context
import android.content.Intent
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
    private val clickListener: OnClickListener
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
            holder.binding.sourceText.text = "By: " + article.author
            Log.i("NewsRecyclerAdapter", "author " + article.author)
        } else {
            holder.binding.sourceText.text = "By: " + article.source.name
            Log.i("NewsRecyclerAdapter", "sourse " + article.source.name)
        }
        holder.binding.dateText.text = article.publishedAt.slice(0..9)
        holder.binding.descText.text = article.description
        Glide.with(context).load(article.urlToImage).placeholder(R.drawable.photo)
            .into(holder.binding.roundedImageView)
        holder.binding.newsCardView.setOnClickListener { clickListener.onClick(article) }
        holder.binding.shareButton.setOnClickListener {
            var intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, "Did You Hear The News! ${article.url}")
            intent.type = "text/plain"
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = articlesList.size
}

class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)