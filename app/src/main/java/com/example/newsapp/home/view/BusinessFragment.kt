package com.example.newsapp.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.home.viewModel.HomeViewModel
import com.example.newsapp.home.viewModel.HomeViewModelFactory
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsService


class BusinessFragment : Fragment() {

    private val binding by lazy { FragmentBusinessBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(
        this, factory = HomeViewModelFactory(
            Repository.getInstance(NewsService.getInstance(), requireContext())
        )
    )
        .get(HomeViewModel::class.java) }
    private val newsAdapter by lazy { NewsRecyclerAdapter(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.newsRecyclerView.adapter = newsAdapter

        viewModel.getNews("business")

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter.setArticlesList(it.articles)

            Log.i("BusinessFragment", ""+it.articles[2].content)
        }
        return binding.root
    }

}