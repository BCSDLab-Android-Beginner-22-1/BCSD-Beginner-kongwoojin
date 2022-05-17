package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment

class RainbowFragment(private val position: Int, private val colorList: MutableList<Rainbow>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_rainbow, container, false)
        val colorName: TextView = rootView.findViewById(R.id.color_name)

        rootView.setBackgroundColor(colorList[position].color)
        colorName.text = colorList[position].colorName

        return rootView
    }
}