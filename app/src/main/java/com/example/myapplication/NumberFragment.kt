package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NumberFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_number, container, false)
        val numberText: TextView = rootView.findViewById(R.id.number_text)

        val number = requireArguments().getInt("number")

        numberText.text = number.toString()

        return rootView
    }
}