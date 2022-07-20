package com.valentine.note.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Notes")
class Notes(
    @PrimaryKey
    val noteId: String,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "date&Time")
    val dateTime: String
)