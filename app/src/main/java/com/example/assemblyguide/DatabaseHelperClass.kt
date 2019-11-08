package com.example.assemblyguide

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.io.IOException


val DATABASE_NAME: String ="bedden.db"
val TABLE_NAME: String = "bedTable"

class DatabaseHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    var db : SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, ARTNR, NOTES, IMAGES)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }



    fun addBed(artNr: String, notes: String){
        var values = ContentValues()
        with(values) {
            put("artNr", artNr)
            put("notes", notes)
            put("images", "")
        }
        val success = db.insert(TABLE_NAME, null, values)
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

    fun searchBeds(searchTerm: String) : List<Bed>?{
        var results : List<Bed> = listOf()
        val searchQuery = "SELECT * FROM bedTable WHERE artNr LIKE  '%$searchTerm%'"
        val cursor = db.rawQuery(searchQuery, null)

        cursor.use{
            it.moveToFirst()

            if(it.count >0){
                do{
                    results = results.plus(
                        Bed(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("ARTNR")),
                        cursor.getString(cursor.getColumnIndex("NOTES")),
                        cursor.getString(cursor.getColumnIndex("IMAGES"))))

                }while (it.moveToNext())
            }
            else{

            }
        }
        return when (results.count() > 0){
            true -> results
            false -> null
        }

    }


    fun getBedById(id: String):Bed?{
        val cursor = db.rawQuery("SELECT * FROM bedTable WHERE id = $id", null)
        cursor.use {
            it.moveToFirst()
            return if(it.count > 0){
                    Bed(cursor.getInt(cursor.getColumnIndex("ID")),
                    cursor.getString(cursor.getColumnIndex("ARTNR")),
                    cursor.getString(cursor.getColumnIndex("NOTES")),
                    cursor.getString(cursor.getColumnIndex("IMAGES")))
            }
            else{
                return null
            }

        }

    }

    fun update (updatedBed : ContentValues) : Int{
        return db.update("bedTable", updatedBed,"ID = ${updatedBed.get("ID")}", null)

    }

    fun delete(id : Int?){
        db.delete("bedTable", "ID = $id", null)
    }
}