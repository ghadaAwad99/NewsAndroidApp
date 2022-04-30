package com.example.newsapp.home.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragmentsList: ArrayList<Fragment> = ArrayList()
    private var fragmentsTitleList: ArrayList<String> = ArrayList()

    override fun getCount() = fragmentsList.size

    override fun getItem(position: Int) = fragmentsList.get(position)

    //override fun getPageTitle(position: Int) = fragmentsTitleList.get(position)

    fun addFragment(fragment: Fragment) {
        fragmentsList.add(fragment)
        //fragmentsTitleList.add(title)
    }

}