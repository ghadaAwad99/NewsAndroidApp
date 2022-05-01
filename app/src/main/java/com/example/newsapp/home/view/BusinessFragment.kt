package com.example.newsapp.home.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.home.viewModel.HomeViewModel
import com.example.newsapp.home.viewModel.HomeViewModelFactory
import com.example.newsapp.model.Article
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsService
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BusinessFragment : Fragment(), OnClickListener {

    var query:String = ""

    private val CATEGORY = "business"

    private val binding by lazy { FragmentBusinessBinding.inflate(layoutInflater) }

    private val viewModel by lazy {
        ViewModelProvider(
            this, factory = HomeViewModelFactory(
                Repository.getInstance(NewsService.getInstance(), requireContext())
            )
        )
            .get(HomeViewModel::class.java)
    }
    private val newsAdapter by lazy { NewsRecyclerAdapter(requireContext(), this) }

    override fun onResume() {
        super.onResume()
        fetchNews(query)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.newsRecyclerView.adapter = newsAdapter

        binding.refreshLayout.setOnRefreshListener {
            fetchNews(query)
        }

        if (isOnline(requireContext())) {
            binding.offlineImage.visibility = View.GONE
            var job: Job? = null
            fetchNews(query)

            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryKeyWord: String?): Boolean {
                    binding.searchBar.clearFocus()
                    if (!queryKeyWord.isNullOrEmpty()) {
                        viewModel.getNews(CATEGORY, q = queryKeyWord)
                    } else {
                        viewModel.getNews(CATEGORY)
                    }
                    return false
                }

                override fun onQueryTextChange(queryKeyWord: String?): Boolean {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(700L)
                        if (!queryKeyWord.isNullOrEmpty()) {
                            query = queryKeyWord
                        } else {
                            query = ""
                        }
                        viewModel.getNews(CATEGORY, query)
                    }
                    return false
                }

            })
        } else {
            Toast.makeText(requireContext(), "You are offline", Toast.LENGTH_LONG).show()

            binding.offlineImage.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun onClick(article: Article) {
        var intent = Intent(requireContext(), ArticleActivity::class.java)
        intent.putExtra("article", article)
        startActivity(intent)
    }


    companion object {
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            }
            return false
        }
    }

    private fun fetchNews(query:String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val language:String = sharedPreferences.getString("language", "us")!!
        val sortBy:String = sharedPreferences.getString("sortBy", "popularity")!!
        if (isOnline(requireContext())) {
            binding.refreshLayout.isRefreshing = true
            viewModel.getNews(q = query, category = CATEGORY, language = language, sortBy = sortBy)
            viewModel.newsLiveData.observe(viewLifecycleOwner) {
                binding.refreshLayout.isRefreshing = false
                if (it.articles.isNotEmpty()) {newsAdapter.setArticlesList(it.articles)}
                else {Toast.makeText(requireContext(), "No Results For This Search", Toast.LENGTH_LONG).show()}
            }
        }
    }

}