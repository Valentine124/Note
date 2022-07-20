package com.valentine.note

import android.app.Application
import com.valentine.note.db.NoteDatabase

class NoteApplication : Application() {

    private val noteDatabase by lazy { NoteDatabase.getDatabase(this) }
    val noteRepository by lazy { NoteRepository(noteDatabase.noteDao()) }
}