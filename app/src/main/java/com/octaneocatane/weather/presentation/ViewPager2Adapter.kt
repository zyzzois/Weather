package com.octaneocatane.api.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fa: AppCompatActivity, private val list: List<Fragment>): FragmentStateAdapter(fa) {
    override fun getItemCount() = list.size
    override fun createFragment(position: Int) = list[position]
 }

