package com.example.assemblyguide

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


val DATABASE_NAME: String ="bedden.db"
val TABLE_NAME: String = "bedTable"
val COL_1: String = "artNr"
val COL_2: String = "notes"
val Col_3: String = "images"

class DatabaseHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    var db : SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {

        Log.i("DB", "BEFORE TABLE CREATION")

        db?.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, ARTNR, NOTES, IMAGES)")

        Log.i("DB", "Database bad.db was created")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun getName(): String {
        Log.i("DB", "IN getName")
        return "GOT NAME HERE"
    }

    fun addBed(artNr: String, notes: String){
        Log.i("DB", "IN INSERT FUN")
        //db.execSQL("INSERT INTO $TABLE_NAME (ARTNR, NOTES, IMAGES) VALUES($artNr , $notes, '')")
        //db.execSQL("INSERT INTO bedTable(artNr, notes, images) VALUES($artNr, $notes, 'ingen bild') ;")
        var values = ContentValues()
        with(values) {
            put("artNr", artNr)
            put("notes", notes)
            put("images", "")
        }

        val success = db.insert(TABLE_NAME, null, values)

        Log.i("DB", "INSERTED ID $success")
    }
}