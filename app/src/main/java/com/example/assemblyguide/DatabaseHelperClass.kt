package com.example.assemblyguide

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


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

    fun search(searchTerm : String) : String {
        var results = ""
        val searchQuary = "SELECT * FROM bedTable WHERE artNr LIKE  '%$searchTerm%'"

        val cursor = db.rawQuery(searchQuary, null)

        cursor.use {
            cursor.moveToFirst()
            if (cursor.count > 0) {
                do {
                    results = results.plus(cursor.getInt(cursor.getColumnIndex("ID"))).toString()
                    results = results.plus(cursor.getString(cursor.getColumnIndex("ARTNR")))
                    results = results.plus((cursor.getString(cursor.getColumnIndex("NOTES"))))
                    results = results.plus(cursor.getString(cursor.getColumnIndex("IMAGES")))
                    results = results.plus("\n\n\n")
                } while (cursor.moveToNext())
            }
            else{
                results = "Nothing found"
            }

        }

        return results
    }

    fun searchBeds(searchTerm: String) : List<Bed>{
        var results : List<Bed> = listOf()
        val searchQuery = "SELECT * FROM bedTable WHERE artNr LIKE  '%$searchTerm%'"
        val cursor = db.rawQuery(searchQuery, null)

        cursor.use{
            it.moveToFirst()

            if(it.count >0){
                do{
                    results = results.plus(
                        Bed(cursor.getInt(cursor.getColumnIndex("ID")).toString(),
                        cursor.getString(cursor.getColumnIndex("ARTNR")),
                        cursor.getString(cursor.getColumnIndex("NOTES"))))

                }while (it.moveToNext())
            }
            else{

            }
        }
        return results

    }
}