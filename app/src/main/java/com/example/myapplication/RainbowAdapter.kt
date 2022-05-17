package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RainbowAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var colorList = mutableListOf<Rainbow>()

    override fun getItemCount(): Int = colorList.size

    override fun createFragment(position: Int): Fragment {
        return RainbowFragment(position, colorList)
    }
}