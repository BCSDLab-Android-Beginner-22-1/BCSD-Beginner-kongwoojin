package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myapplication.Rainbow

class MainActivity : AppCompatActivity() {
    val colorList = mutableListOf<Rainbow>()
    val numberList = mutableListOf<Int>()
    val alphabetList = mutableListOf<Char>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        val rainbowAdapter = RainbowAdapter(this)

        colorList.add(Rainbow("Red", Color.RED))
        colorList.add(Rainbow("Orange", Color.parseColor("#ffa500")))
        colorList.add(Rainbow("Yellow", Color.YELLOW))
        colorList.add(Rainbow("Green", Color.GREEN))
        colorList.add(Rainbow("Blue", Color.BLUE))
        colorList.add(Rainbow("Indigo", Color.parseColor("#233067")))
        colorList.add(Rainbow("Purple", Color.parseColor("#ee82ee")))
        rainbowAdapter.colorList = colorList

        val numberAdapter = NumberAdapter(this)
        numberList.addAll(1..10)
        numberAdapter.numberList = numberList

        val alphabetAdapter = AlphabetAdapter(this)
        alphabetList.addAll(('A'..'Z'))
        alphabetAdapter.alphabetList = alphabetList


        viewPager.adapter = rainbowAdapter

        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_rainbow -> {
                    viewPager.adapter = rainbowAdapter
                    true
                }
                R.id.menu_number -> {
                    viewPager.adapter = numberAdapter
                    true
                }
                R.id.menu_alphabet -> {
                    viewPager.adapter = alphabetAdapter
                    true
                }
                else -> false
            }
        }
    }
}