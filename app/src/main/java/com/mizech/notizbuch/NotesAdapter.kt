package com.mizech.notizbuch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(private var notes: ArrayList<Note>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var noteTitle: TextView? = null

        init {
            noteTitle = itemView.findViewById(R.id.noteTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                                .inflate(R.layout.note_item, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.noteTitle.text = notes[position].title
        holder.itemView.noteTitle.setOnClickListener {
            val intent = Intent(it.context, UpdateNote::class.java)

            intent.putExtra("noteId", notes[position].id)
            intent.putExtra("noteTitle", notes[position].title)
            intent.putExtra("noteText", notes[position].text)
            intent.putExtra("noteCategory", notes[position].category)
            it.context.startActivity(intent)
        }
    }

}