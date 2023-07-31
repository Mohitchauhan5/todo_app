package com.example.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_table")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val note : String,
    val date : String,
)



