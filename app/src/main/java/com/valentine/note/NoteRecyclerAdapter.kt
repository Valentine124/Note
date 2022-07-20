package com.valentine.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentine.note.db.Notes

open class NoteRecyclerAdapter : ListAdapter<Notes, NoteRecyclerAdapter.NoteViewHolder>(NoteComparator()) {

    private lateinit var mlistener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent, mlistener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote.note, currentNote.dateTime, currentNote)
    }

    class NoteViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val noteText = itemView.findViewById<TextView>(R.id.note_snapshop)
        private val noteTime = itemView.findViewById<TextView>(R.id.note_date_time)
        private lateinit var currentNotes: Notes

        fun bind(noteTexts: String?, time: String?, note: Notes) {
            noteText.text = noteTexts
            noteTime.text = time
            currentNotes = note
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClickListener(currentNotes)
            }
        }
        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): NoteViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.note_items, parent, false)
                return NoteViewHolder(view, listener)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClickListener(note: Notes)
    }

    class NoteComparator : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.note == newItem.note && oldItem.dateTime == newItem.dateTime
        }

    }
}