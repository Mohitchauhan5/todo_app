package com.example.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import com.example.notes.helper.NotesDatabase
import com.example.notes.model.CheckNotes
import com.example.notes.model.Notes

class NotesRepository(private val notesDatabase: NotesDatabase) {

    val notesLiveData : LiveData<List<Notes>> = notesDatabase.getNotesDao().getNotes()
    var list = listOf<Notes>()


   val checkNotesLiveData : LiveData<List<CheckNotes>> = notesDatabase.getNotesDao().getCheckNotes()



    private val _searchLiveData  = MutableLiveData<String>()

    val searchLiveData : LiveData<String>
    get() = _searchLiveData

    suspend fun insertNotes(notes: Notes){
        notesDatabase.getNotesDao().insertData(notes)
    }

     fun insertSearchData(search : String){
        _searchLiveData.postValue(search)
    }


    suspend fun updateNotes(notes: Notes){
        notesDatabase.getNotesDao().updateNotes(notes)
    }

    suspend fun insertCheckNotes(checkNotes: CheckNotes){
        notesDatabase.getNotesDao().insertCheckNotes(checkNotes)
    }

    suspend fun customCheckNotes(ide : Int) : List<CheckNotes>{
       return notesDatabase.getNotesDao().getCustomCheckNotes(ide)
    }

    suspend fun updateCheckNotes(checkNotes: CheckNotes)  {
        notesDatabase.getNotesDao().updateCheckNotes(checkNotes)
    }




    suspend fun deleteNote(notes: Notes){

        notesDatabase.getNotesDao().deleteNote(notes)

    }


    suspend fun deleteCheckNote(checkNotes: CheckNotes){
        notesDatabase.getNotesDao().deleteCheckNote(checkNotes)


    }





//    fun getSearch() : List<Notes>{
//        return notesDatabase.getNotesDao().searchNotes(5)
//    }
}