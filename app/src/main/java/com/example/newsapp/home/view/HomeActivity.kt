package com.example.newsapp.home.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class HomeActivity : AppCompatActivity(){

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        //binding.tabLayout.setupWithViewPager(binding.viewPager)


       // tabLayout.addTab(tabLayout.newTab().setText("Chats").setIcon(R.drawable.ic_chat_24))
        //tabLayout.addTab(tabLayout.newTab().setText("Calls").setIcon(R.drawable.ic_call_24))





        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("General").setIcon(R.drawable.ic_baseline_public_24))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Sports").setIcon(R.drawable.ic_sport))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Business").setIcon(R.drawable.ic_baseline_business_center_24))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("entertainment").setIcon(R.drawable.ic_entertainment))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("health").setIcon(R.drawable.ic_health))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("science").setIcon(R.drawable.ic_baseline_science_24))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("technology").setIcon(R.drawable.ic_technology))

        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(GeneralFragment())
        viewPagerAdapter.addFragment(SportsFragment())
        viewPagerAdapter.addFragment(BusinessFragment())
        viewPagerAdapter.addFragment(EntertainmentFragment())
        viewPagerAdapter.addFragment(HealthFragment())
        viewPagerAdapter.addFragment(ScienceFragment())
        viewPagerAdapter.addFragment(TechnologyFragment())


        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.setOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager))


       // val navHostFragment =
            //supportFragmentManager.findFragmentById(R.id.news_nav_host_fragment) as NavHostFragment
        //val navController = navHostFragment.navController
       //navController.graph = navController.navInflater.inflate(R.navigation.news_nav_graph)

    }




}