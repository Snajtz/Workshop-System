package com.example.assemblyguide

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    lateinit var listView : ListView
    lateinit var db : DatabaseHelper
    var bedsList : List<Bed>? = null
    lateinit var adapter : BedListViewAdapter


    fun updateList(term : String) {
        bedsList = db.searchBeds(term)
        adapter = BedListViewAdapter(this@MainActivity, bedsList)
        listView.adapter = adapter

        when (bedsList) {
            null -> noMatches.text = "No matches found"
            else -> noMatches.text = null

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.searchResultsList)
        db = DatabaseHelper(this)

        updateList("")

        submitButton.setOnClickListener{
            db.addBed(artNrInput.text.toString(), notesInput.text.toString())
            artNrInput.text = null
            notesInput.text = null
            updateList(searchInput.text.toString())
        }


        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                updateList(s.toString())

            }

        })
    }

}

