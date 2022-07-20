package com.valentine.note

import androidx.lifecycle.*
import com.valentine.note.db.Notes
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Notes>> = noteRepository.notes.asLiveData()

    fun addNotes(note: Notes) = viewModelScope.launch {
        noteRepository.addNote(note)
    }

    fun removeNote(note: Notes) = viewModelScope.launch {
        noteRepository.removeNote(note)
    }

    fun updateNote(id: String, note: String, date: String) {
        val updatedNote = getUpdateNoteEntry(id, note, date)
        updateNote(updatedNote)
    }

    private fun updateNote(note: Notes) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    private fun getUpdateNoteEntry(noteId: String, note: String, noteDateTime: String) : Notes {
        return Notes(noteId = noteId, note = note, dateTime = noteDateTime)
    }
}

class NoteViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}