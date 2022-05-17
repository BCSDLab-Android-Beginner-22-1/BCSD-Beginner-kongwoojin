package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NumberAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var numberList = mutableListOf<Int>()

    override fun getItemCount(): Int = numberList.size

    override fun createFragment(position: Int): Fragment {
        val numberFragment = NumberFragment()
        val bundle = Bundle()
        bundle.putInt("number", numberList[position])
        numberFragment.arguments = bundle
        return numberFragment
    }
}