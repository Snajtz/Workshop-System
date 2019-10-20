package com.example.assemblyguide

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var searchTerm = textEntry.text

        Log.i("MAIN","Line before db creation")

        var db = DatabaseHelper(this)

        Log.i("MAIN","Line efter db creation")

        textBox.text = "Synthetic property"

        testButton.setOnClickListener{
            textBox.text = textEntry.text
            db.addBed(textEntry.text.toString(), "EN vanlig vit säng")
        }


    }


}

