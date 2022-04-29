package com.example.newsapp.model

import android.content.Context
import android.util.Log
import com.example.newsapp.network.NewsInterface

class Repository private constructor(
    private val remoteSource: NewsInterface,
    context: Context
) : RepositoryInterface {

    companion object {
        private var instance: Repository? = null
        fun getInstance (newsInterface: NewsInterface ,context: Context): Repository {
            Log.i("TAG", "inside repo get instance")
            return instance ?: Repository(newsInterface, context)
        }
    }

    override suspend fun getNewsFromApi(category:String) = remoteSource.getNewsResponse(category = category)

}