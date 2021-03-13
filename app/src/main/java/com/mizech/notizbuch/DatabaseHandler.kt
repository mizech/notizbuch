package com.mizech.notizbuch

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "notebook"
        private const val TABLE = "notes"

        private const val ID = "_ID"
        private const val TITLE = "title"
        private const val NOTE = "note"
        private const val CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable
                = "CREATE TABLE $TABLE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TITLE TEXT, " +
                    "$NOTE TEXT, $CATEGORY INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }
}