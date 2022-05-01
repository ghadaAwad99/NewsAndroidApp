package com.example.newsapp.model

import retrofit2.Response

interface RepositoryInterface {
    suspend fun getNewsFromApi(category:String, q: String,country:String,sortBy:String, language:String) : Response<ModelClass>
}