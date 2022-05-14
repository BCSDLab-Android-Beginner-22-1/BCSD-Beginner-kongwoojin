package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val dataList = mutableListOf<Names>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val addItemText: EditText = findViewById(R.id.add_item_text)
        val addItemButton: Button = findViewById(R.id.add_item_button)

        val adapter = NameAdapter()
        adapter.dataList = dataList
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addItemButton.setOnClickListener {
            if (addItemText.text.isNotBlank()) {
                dataList.add(Names(addItemText.text.toString()))
                addItemText.setText("")
                adapter.notifyItemInserted(adapter.itemCount)
            } else {
                Toast.makeText(this, getString(R.string.name_empty), Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_delete_title))
                .setMessage(getString(R.string.dialog_delete_message))
                .setPositiveButton(
                    getString(R.string.dialog_delete_confirm)
                ) { _, _ ->
                    dataList.removeAt(it)
                    adapter.notifyItemRemoved(it)
                }
                .setNegativeButton(
                    getString(R.string.dialog_delete_dismiss)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.show()
        }

        adapter.setOnLongClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater

            val rootView = inflater.inflate(R.layout.dialog_edit, null)
            builder.setTitle(getString(R.string.dialog_change_title))
                .setView(rootView)
                .setPositiveButton(
                    getString(R.string.dialog_change_confirm)
                ) { _, _ ->
                    val changeEditText: EditText = rootView.findViewById(R.id.change_edit_text)
                    if (changeEditText.text.isNotBlank()) {
                        dataList[it] = Names(changeEditText.text.toString())
                        adapter.notifyItemChanged(it)
                    } else {
                        Toast.makeText(this, getString(R.string.name_empty), Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton(
                    getString(R.string.dialog_change_dismiss)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.show()
        }

    }
}