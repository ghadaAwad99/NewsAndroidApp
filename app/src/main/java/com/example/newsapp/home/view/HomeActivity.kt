package com.example.newsapp.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.home.view.fragments.*
import com.example.newsapp.settings.SettingsActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class HomeActivity : AppCompatActivity(){

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("HomeActivity", "inside HomeActivity")


        //binding.tabLayout.setupWithViewPager(binding.viewPager)


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings){
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }


}