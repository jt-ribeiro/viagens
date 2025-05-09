package com.example.viagens.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viagens.Slide1Fragment
import com.example.viagens.Slide2Fragment
import com.example.viagens.Slide3Fragment

class SliderPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf(
        Slide1Fragment(),
        Slide2Fragment(),
        Slide3Fragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}