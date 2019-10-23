package com.example.assemblyguide

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity

class objectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.object_activity)

    }

    override fun onCreateOptionsMenu(menu : Menu) : Boolean{
        var inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.object_activity_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}