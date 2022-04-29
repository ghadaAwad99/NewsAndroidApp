package com.example.newsapp.model

data class ModelClass(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)