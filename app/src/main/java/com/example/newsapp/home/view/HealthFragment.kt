package com.example.newsapp.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.FragmentHealthBinding
import com.example.newsapp.home.viewModel.HomeViewModel
import com.example.newsapp.home.viewModel.HomeViewModelFactory
import com.example.newsapp.model.Article
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsService
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HealthFragment : Fragment(), OnClickListener {

    private val binding by lazy { FragmentHealthBinding.inflate(layoutInflater) }

    var query: String = ""

    private val viewModel by lazy {
        ViewModelProvider(
            this, factory = HomeViewModelFactory(
                Repository.getInstance(NewsService.getInstance(), requireContext())
            )
        )
            .get(HomeViewModel::class.java)
    }
    private val newsAdapter by lazy { NewsRecyclerAdapter(requireContext(), this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.newsRecyclerView.adapter = newsAdapter

        var job: Job? = null

        viewModel.getNews("health")

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchBar.clearFocus()
                if (!p0.isNullOrEmpty()) {
                    viewModel.getNews("health", q = p0)
                } else {
                    viewModel.getNews("health")
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(700L)
                    if (!p0.isNullOrEmpty()) {
                        query = p0
                    } else {
                        query = ""
                    }
                    viewModel.getNews("health", query)
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
