package com.example.notes.api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.model.CheckNotes
import com.example.notes.model.Notes


@Dao
interface NotesDao {
    @Insert
    suspend fun insertData(notes: Notes)


    @Query("SELECT * FROM note_table")
    fun getNotes() : LiveData<List<Notes>>

    @Update
    suspend fun updateNotes(notes: Notes)

    @Insert
    suspend fun insertCheckNotes(checkNotes: CheckNotes)

    @Query("SELECT * FROM check_notes_table")
    fun getCheckNotes() : LiveData<List<CheckNotes>>

    @Query("SELECT * FROM check_notes_table WHERE id = :ide")
    suspend fun getCustomCheckNotes(ide : Int) : List<CheckNotes>

    @Update
    suspend fun updateCheckNotes(checkNotes: CheckNotes)


    @Delete
    suspend fun deleteNote(notes: Notes)

    @Delete
    suspend fun deleteCheckNote(checkNotes: CheckNotes)


}