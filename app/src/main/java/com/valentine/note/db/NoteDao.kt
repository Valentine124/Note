package com.valentine.note.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notes: Notes)

    @Delete
    suspend fun removeNote(notes: Notes)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotes(notes: Notes)
}