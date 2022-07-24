package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragment.FilmListFragment
import com.example.myapplication.fragment.PersonListFragment
import com.example.myapplication.fragment.PlanetListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val filmListFragment = FilmListFragment()
        val personListFragment = PersonListFragment()
        val planetListFragment = PlanetListFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, filmListFragment)
            .commit()

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_films -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, filmListFragment)
                        .commit()
                    true
                }
                R.id.navigation_people -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, personListFragment)
                        .commit()
                    true
                }
                R.id.navigation_planets -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, planetListFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}