package com.example.newsapp.model

data class NewsResponseModel(
    val sources: List<Source>,
    val status: String
)