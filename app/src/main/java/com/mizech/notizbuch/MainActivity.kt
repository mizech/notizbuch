package com.mizech.notizbuch

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var notes = ArrayList<Note>()
    private var adapter: NotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbh = DatabaseHandler(this)
        val wdb = dbh.writableDatabase

        val cursor = wdb.rawQuery("SELECT * FROM notes", null)
        cursor.moveToFirst()

        var hasNext = cursor.moveToFirst()

        while (hasNext) {
            var id = cursor.getInt(0)
            var title = cursor.getString(1)
            var text = cursor.getString(2)
            var category = cursor.getString(3)

            notes.add(Note(id, title, text, category.toInt()))
            hasNext = cursor.moveToNext()
        }

        adapter = NotesAdapter(notes)
        notesList.adapter = adapter
        notesList.layoutManager = LinearLayoutManager(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val dbh = DatabaseHandler(this)
        val wdb = dbh.writableDatabase

        val cursor = wdb.rawQuery("SELECT * FROM notes", null)
        val count = cursor.count

        menu?.getItem(1)?.isEnabled = count != 0

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_note -> {
                val intent = Intent(this, InsertNote::class.java)
                startActivity(intent)
                true
            }
            R.id.delete_list -> {
                val dbh = DatabaseHandler(this)
                  val wdb = dbh.writableDatabase
                val dialog = AlertDialog.Builder(this)

                dialog.setTitle(getString(R.string.ask_delete_list))
                dialog.setMessage(getString(R.string.ask_continue))

                dialog.setPositiveButton(R.string.yes) { dialog, _ ->
                    wdb?.execSQL("DELETE FROM notes")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, R.string.confirm_all_deleted,
                        Toast.LENGTH_LONG).show();
                }
                dialog.setNegativeButton(R.string.no) { dialog, _ ->
                    Toast.makeText(applicationContext, R.string.confirm_cancel,
                        Toast.LENGTH_LONG).show();
                }

                dialog.show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}