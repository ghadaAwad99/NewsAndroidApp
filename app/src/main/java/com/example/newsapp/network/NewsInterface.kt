package com.example.newsapp.network

import com.example.newsapp.model.ModelClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("top-headlines")
    suspend fun getNewsResponse(
        @Query("apiKey") key: String = "e9e48500298a4b6f853c52fb015b12ac",
        @Query("category") category: String,
        @Query("q") searchKeyWord: String = "",
        @Query("country") country:String = "",
        @Query("sortBy") sortBy:String = "",
        @Query("language") language:String = ""
    ): Response<ModelClass>
}
//https://newsapi.org/v2/top-headlines?category=science&apiKey=64f90b33dec84d49923062f4706beacd&country=us