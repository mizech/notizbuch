package com.mizech.notizbuch

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_single_note.*

class UpdateNote : AppCompatActivity() {
    private var iId: Int = -1
    private var sNoteTitle: String = ""
    private var sNoteText: String = ""
    private var iCategory: Int = 0
    private val dbh = DatabaseHandler(this)
    private var wDatabase: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_note)

        wDatabase = dbh.writableDatabase

        iId = intent.getIntExtra("noteId", -1)
        sNoteTitle = intent.getStringExtra("noteTitle").toString()
        sNoteText = intent.getStringExtra("noteText").toString()
        iCategory = intent.getIntExtra("noteCategory", 3)

        singleNoteTitle.setText(sNoteTitle)
        singleNoteText.setText(sNoteText)
        spinnerCategory.setSelection(iCategory)

        deleteNote.setOnClickListener { _ ->
            val dialog = AlertDialog.Builder(this)

            dialog.setTitle(getString(R.string.ask_delete_note))
            dialog.setMessage(R.string.ask_continue)

            dialog.setPositiveButton(R.string.yes) { dialog, _ ->
                wDatabase?.delete("notes", "_ID=$iId", null)
                navigateBack()
                Toast.makeText(applicationContext, R.string.confirm_single_deleted,
                    Toast.LENGTH_LONG).show();
            }
            dialog.setNegativeButton(R.string.no) { dialog, _ ->
                Toast.makeText(applicationContext, R.string.confirm_cancel,
                    Toast.LENGTH_LONG).show();
            }

            dialog.show()
        }

        updateNote.setOnClickListener {
            if (title.length >= 3) {
                var cv = ContentValues()
                cv.put("title", singleNoteTitle.text.toString())
                cv.put("note", singleNoteText.text.toString())
                cv.put("category", spinnerCategory.selectedItemPosition)
                wDatabase?.update("notes", cv,
                    "_id=?", arrayOf(iId.toString()))
            } else {
                Toast.makeText(applicationContext,
                    R.string.title_min_length,
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