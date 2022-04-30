package com.example.newsapp.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.NewsResponseModel
import com.example.newsapp.model.RepositoryInterface
import java.net.SocketTimeoutException
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.ModelClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(val repository: RepositoryInterface) : ViewModel() {
    private val newsMutableLiveData: MutableLiveData<ModelClass> = MutableLiveData()
    val newsLiveData: LiveData<ModelClass> = newsMutableLiveData

    fun getNews(category:String, q :String ="") {
        Log.i("TAG", "inside getCurrTemp")
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TAG", "inside CoroutineScope")
            try {
                val response = repository.getNewsFromApi(category, q = q)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        newsMutableLiveData.postValue(response.body())
                    } else {
                        Log.e("HomeViewModel", "Error fetching data in HomeViewModel ${response.message()}" )
                    }
                }
            } catch (ex: SocketTimeoutException) {
                Log.e("TAG", ex.message.toString())
            }
        }
    }
}