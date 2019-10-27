package com.example.assemblyguide

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.object_activity.*

class ObjectActivity : AppCompatActivity() {
    var editMode = false
    var bed :Bed? = null
    var db : DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.object_activity)

        bed = intent.getSerializableExtra("bed") as Bed
        editTextArtNr.setText(bed?.artNr)
        editTextNotes.setText(bed?.notes)
        this.db = DatabaseHelper(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.object_activity_menu, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId){
        R.id.editObjectBtn -> {
            if (!editMode) {
                editMode = true
                item.setIcon(R.drawable.baseline_check_black_18dp)
                editTextArtNr.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                editTextNotes.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                editTextArtNr.setTextIsSelectable(true)
                editTextNotes.setTextIsSelectable(true)
            }
            else{
                val updatedBed = ContentValues()
                updatedBed.put("ID", bed?.id)
                updatedBed.put("ARTNR", editTextArtNr.text.toString())
                updatedBed.put("NOTES", editTextNotes.text.toString())
                db?.update(updatedBed)


                editMode = false
                item.setIcon(R.drawable.baseline_edit_black_24)
                editTextArtNr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                editTextNotes.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                editTextArtNr.setTextIsSelectable(false)
                editTextNotes.setTextIsSelectable(false)
            }
            true
        }
        R.id.deleteObjectBtn -> {
            db?.delete(bed?.id)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            true
        }
            else -> super.onOptionsItemSelected(item)

    }

}