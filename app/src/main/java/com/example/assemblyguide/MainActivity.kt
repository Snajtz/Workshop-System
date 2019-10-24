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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var searchTerm = artNrInput.text

        Log.i("MAIN","Line before db creation")

        var db = DatabaseHelper(this)

        Log.i("MAIN","Line efter db creation")

        submitButton.setOnClickListener{
            db.addBed(artNrInput.text.toString(), notesInput.text.toString())
        }


        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val listView : ListView = findViewById(R.id.searchResultsList)
                val bedsList = db.searchBeds(s.toString())
                if(bedsList != null) {
                    var adapter = BedListViewAdapter(this@MainActivity, bedsList)
                    listView.adapter = adapter

                }
                else {
                    val bed = Bed("123", "test")
                    testDisplay.text = bed.artNr
                }
            }

        })

    }


}

