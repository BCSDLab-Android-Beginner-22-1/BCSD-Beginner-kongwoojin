package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RainbowAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var colorList = mutableListOf<Rainbow>()

    override fun getItemCount(): Int = colorList.size

    override fun createFragment(position: Int): Fragment {
        val rainbowFragment = RainbowFragment()
        val bundle = Bundle()
        bundle.putInt("color", colorList[position].color)
        bundle.putString("color_name", colorList[position].colorName)
        rainbowFragment.arguments = bundle
        return rainbowFragment
    }
}