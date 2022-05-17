package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NumberAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var numberList = mutableListOf<Int>()

    override fun getItemCount(): Int = numberList.size

    override fun createFragment(position: Int): Fragment {
        return NumberFragment(position, numberList)
    }
}