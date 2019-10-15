package com.example.assemblyguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var searchTerm = textEntry.text

        textBox.text = "Synthetic property"

        testButton.setOnClickListener{
            textBox.text = textEntry.text

        }




    }

}

