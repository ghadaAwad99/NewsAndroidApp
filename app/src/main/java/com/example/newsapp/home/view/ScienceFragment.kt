package com.example.newsapp.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.databinding.FragmentScienceBinding
import com.example.newsapp.home.viewModel.HomeViewModel
import com.example.newsapp.home.viewModel.HomeViewModelFactory
import com.example.newsapp.model.Article
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsService
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ScienceFragment : Fragment(), OnClickListener {

    var query:String = ""

    private val binding by lazy { FragmentScienceBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(
        this, factory = HomeViewModelFactory(
            Repository.getInstance(NewsService.getInstance(), requireContext())
        )
    )
        .get(HomeViewModel::class.java) }
    private val newsAdapter by lazy { NewsRecyclerAdapter(requireContext(), this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.newsRecyclerView.adapter = newsAdapter

        var job :  Job? = null

        viewModel.getNews("sports")

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchBar.clearFocus()
                if (!p0.isNullOrEmpty()) {
                    viewModel.getNews("sports", q = p0)
                    Log.i("onQueryTextSubmit", p0)
                } else {
                    viewModel.getNews("sports")
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(700L)
                    if (!p0.isNullOrEmpty()) {
                        query = p0
                        Log.i("onQueryTextSubmit", p0)
                    } else {
                        query = ""
                    }
                    viewModel.getNews("sports", query)
                }
                return false
            }

        })

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter.setArticlesList(it.articles)
        }
        return binding.root
    }


    override fun onClick(article: Article) {
        var intent = Intent(requireContext(), ArticleActivity::class.java)
        intent.putExtra("article", article)
        startActivity(intent)
    }


}