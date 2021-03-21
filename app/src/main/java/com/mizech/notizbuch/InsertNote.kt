package com.mizech.notizbuch

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_note.deleteNote
import kotlinx.android.synthetic.main.activity_create_note.navBack
import kotlinx.android.synthetic.main.activity_create_note.singleNoteText
import kotlinx.android.synthetic.main.activity_create_note.singleNoteTitle
import kotlinx.android.synthetic.main.activity_create_note.spinnerCategory

class InsertNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        deleteNote.setOnClickListener {
            val dbh = DatabaseHandler(this)
            val writableDb = dbh.writableDatabase

            val noteTitle = singleNoteTitle.text.toString()

            if (noteTitle.length > 2) {
                val noteText = singleNoteText.text.toString()
                val category = spinnerCategory.selectedItemPosition

                val cv = ContentValues()
                cv.put("title", noteTitle.trim())
                cv.put("note", noteText.trim())
                cv.put("category", category)

                writableDb.insertWithOnConflict("notes", null, cv,
                    SQLiteDatabase.CONFLICT_IGNORE)
                navigateBack()
            } else {
                Toast.makeText(applicationContext, R.string.title_min_length,
                    Toast.LENGTH_LONG).show();
            }
        }

        navBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}