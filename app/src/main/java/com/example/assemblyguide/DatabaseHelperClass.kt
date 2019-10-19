package com.example.assemblyguide

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val DATABASE_NAME: String ="bed.db"
val TABLE_NAME: String = "bedTable"
val COL_1: String = "artNr"
val COL_2: String = "notes"
val Col_3: String = "images"

class DatabaseHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Log.i("DB", "Database bad.db was created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getName(): String {
        return DATABASE_NAME
    }
}