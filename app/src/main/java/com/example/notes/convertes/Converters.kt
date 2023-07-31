package com.example.notes.convertes

import androidx.room.TypeConverter
import com.example.notes.model.Check
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromStringArrayList(value: List<Check>): String {

        return Gson().toJson(value)
    }
    @TypeConverter
    fun toStringArrayList(value: String): List<Check> {

        val game =  GsonBuilder().create();
       val dat : ArrayList<Check> = game.fromJson(value,  object :TypeToken<ArrayList<Check>>(){}.type)

        val _list = mutableListOf<Check>()

        for (i in dat){
            _list.add(Check(i.status,i.data))

        }

        val list : List<Check>
        list = _list


        return list

    }

}