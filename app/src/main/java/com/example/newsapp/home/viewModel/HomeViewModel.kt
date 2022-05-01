package com.example.newsapp.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.ModelClass
import com.example.newsapp.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class HomeViewModel(val repository: RepositoryInterface) : ViewModel() {
    private val newsMutableLiveData: MutableLiveData<ModelClass> = MutableLiveData()
    val newsLiveData: LiveData<ModelClass> = newsMutableLiveData

    fun getNews(
        category: String,
        q: String = "",
        country: String = "",
        sortBy: String = "",
        language: String = ""
    ) {
        Log.i("TAG", "inside getCurrTemp")
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TAG", "inside CoroutineScope")
            try {
                val response = repository.getNewsFromApi(
                    category = category,
                    q = q,
                    country = country,
                    sortBy = sortBy,
                    language = language
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        newsMutableLiveData.postValue(response.body())
                    } else {
                        Log.e(
                            "HomeViewModel",
                            "Error fetching data in HomeViewModel ${response.message()}"
                        )
                    }
                }
            } catch (ex: SocketTimeoutException) {
                Log.e("TAG", ex.message.toString())
            }
        }
    }
}