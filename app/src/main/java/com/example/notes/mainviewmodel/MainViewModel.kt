package com.example.notes.mainviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    val notesLiveData = notesRepository.notesLiveData

    val checkLiveData = notesRepository.checkNotesLiveData


    fun insertData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.insertNotes(notes)

        }
    }

    fun updateData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNotes(notes)

        }
    }
}