package com.valentine.note


import com.valentine.note.db.NoteDao
import com.valentine.note.db.Notes
import kotlinx.coroutines.flow.*

class NoteRepository(private val noteDao: NoteDao) {

    val notes: Flow<List<Notes>> = noteDao.getNotes()

    suspend fun addNote(note: Notes) {
        noteDao.addNote(note)
    }

    suspend fun removeNote(note: Notes) {
        noteDao.removeNote(note)
    }

    suspend fun updateNote(note: Notes) {
        noteDao.updateNotes(note)
    }
}