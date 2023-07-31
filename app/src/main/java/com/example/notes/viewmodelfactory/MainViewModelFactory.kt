package com.example.notes.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.mainviewmodel.MainViewModel
import com.example.notes.repository.NotesRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val notesRepository: NotesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(notesRepository) as T
    }
}