package com.mizech.notizbuch

import android.content.Context

class RecordsManager {
    companion object {
        fun createNotes(context: Context, times: Int) {
            val dbh = DatabaseHandler(context)
            val writableDb = dbh.writableDatabase

            for (i in 0 until times) {
                writableDb.execSQL("INSERT INTO notes (title, note, category) " +
                        "VALUES ('Some Note-Title ${i}', 'Some Note-Text ${i}', ${(0..3).random()})")
            }
        }

        fun deleteAllNotes(context: Context) {
            val dbh = DatabaseHandler(context)
            val writableDb = dbh.writableDatabase
            writableDb.execSQL("DELETE FROM notes")
        }
    }
}