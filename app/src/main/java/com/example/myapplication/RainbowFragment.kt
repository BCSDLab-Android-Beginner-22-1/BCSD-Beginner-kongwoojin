package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment

class RainbowFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_rainbow, container, false)
        val colorNameText: TextView = rootView.findViewById(R.id.color_name)

        val color = requireArguments().getInt("color")
        val colorName = requireArguments().getString("color_name")

        rootView.setBackgroundColor(color)
        colorNameText.text = colorName

        return rootView
    }
}