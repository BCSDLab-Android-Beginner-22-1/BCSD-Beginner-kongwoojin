package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlphabetAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var alphabetList = mutableListOf<Char>()

    override fun getItemCount(): Int = alphabetList.size

    override fun createFragment(position: Int): Fragment {
        return AlphabetFragment(position, alphabetList)
    }
}