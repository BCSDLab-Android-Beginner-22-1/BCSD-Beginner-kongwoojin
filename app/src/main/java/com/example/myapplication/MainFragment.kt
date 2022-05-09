package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import org.w3c.dom.Text

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        val dialogButton: Button = rootView.findViewById(R.id.dialog_button)
        val countButton: Button = rootView.findViewById(R.id.count_button)
        val randomButton: Button = rootView.findViewById(R.id.random_button)
        val countTextView: TextView = rootView.findViewById(R.id.count_text_view)

        var currentCount = 0


        setFragmentResultListener("requestKey") { _, bundle ->
            currentCount = bundle.getInt("count")
            countTextView.text = currentCount.toString()
        }

        countTextView.text = currentCount.toString()

        dialogButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_text))
                .setPositiveButton(getString(R.string.dialog_reset)) { _, _ ->
                    currentCount = 0
                    countTextView.text = currentCount.toString()
                }
                .setNegativeButton(getString(R.string.dialog_dismiss)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNeutralButton(getString(R.string.dialog_toast)) { _, _ ->
                    Toast.makeText(requireContext(), getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()
                }
            builder.show()
        }

        countButton.setOnClickListener {
            currentCount += 1
            countTextView.text = currentCount.toString()
        }

        randomButton.setOnClickListener {
            val fragment: Fragment = SubFragment()

            val bundle = Bundle().apply {
                putInt("count", currentCount)
            }

            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return rootView
    }
}