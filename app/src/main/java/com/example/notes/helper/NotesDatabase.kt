package com.example.notes.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.api.NotesDao
import com.example.notes.convertes.Converters
import com.example.notes.model.CheckNotes
import com.example.notes.model.Notes


@Database(entities = [Notes::class,CheckNotes::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase(){

    abstract fun getNotesDao() : NotesDao

    companion object{

        @Volatile
        private var instance : NotesDatabase? = null


        fun getInstance(context: Context) : NotesDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context,NotesDatabase::class.java,"notesDb").build()
            }
            return instance as NotesDatabase
        }
    }
}