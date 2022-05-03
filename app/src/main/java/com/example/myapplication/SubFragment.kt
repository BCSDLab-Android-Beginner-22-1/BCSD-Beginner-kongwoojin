package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random

class SubFragment : Fragment() {

    lateinit var countData: CountData

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countData = context as CountData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sub, container, false)

        val currentNumTextView: TextView = rootView.findViewById(R.id.current_num_text_view)
        val infoTextView: TextView = rootView.findViewById(R.id.info_text_view)
        val countFromMain = requireArguments().getInt("count", 0)
        val randomNumber = Random.nextInt(countFromMain + 1)

        infoTextView.text = getString(R.string.random_info, countFromMain)
        currentNumTextView.text = randomNumber.toString()
        passCount(randomNumber)

        return rootView
    }

    private fun passCount(randomNumber: Int) {
        countData.countData(randomNumber)
    }
}