package com.example.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "check_notes_table")
data class CheckNotes(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val list: List<Check>,
    val date: String
)
