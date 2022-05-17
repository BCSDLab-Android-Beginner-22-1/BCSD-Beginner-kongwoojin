package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AlphabetFragment(private val position: Int, private val alphabetList: MutableList<Char>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_alphabet, container, false)
        val alphabetText: TextView = rootView.findViewById(R.id.alphabet_text)

        alphabetText.text = alphabetList[position].toString()

        return rootView
    }
}