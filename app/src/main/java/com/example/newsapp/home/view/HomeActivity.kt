package com.example.newsapp.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.home.viewModel.HomeViewModel
import com.example.newsapp.home.viewModel.HomeViewModelFactory
import com.example.newsapp.model.Article
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsService

class HomeActivity : AppCompatActivity(){

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(GeneralFragment(),"General")
        viewPagerAdapter.addFragment(BusinessFragment(),"Business")
        viewPagerAdapter.addFragment(ScienceFragment(),"Sports")
        binding.viewPager.adapter = viewPagerAdapter

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        fun replaceFragment(fragment:Fragment){
            var fragmentManager = supportFragmentManager
            var fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout, fragment)
            fragmentTransaction.commit()
        }




       // val navHostFragment =
            //supportFragmentManager.findFragmentById(R.id.news_nav_host_fragment) as NavHostFragment
        //val navController = navHostFragment.navController
       //navController.graph = navController.navInflater.inflate(R.navigation.news_nav_graph)

    }




}