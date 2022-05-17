package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlphabetAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var alphabetList = mutableListOf<Char>()

    override fun getItemCount(): Int = alphabetList.size

    override fun createFragment(position: Int): Fragment {
        val alphabetFragment = AlphabetFragment()
        val bundle = Bundle()
        bundle.putChar("alphabet", alphabetList[position])
        alphabetFragment.arguments = bundle
        return alphabetFragment
    }
}