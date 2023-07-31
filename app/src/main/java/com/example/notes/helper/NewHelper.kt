package com.example.notes.helper

import androidx.lifecycle.MutableLiveData
import com.example.notes.model.Check

class NewHelper {

    companion object{

        var Makelist = mutableListOf<Check>()
        val checkStatus  =  MutableLiveData<Int>()
        val imageStatus = MutableLiveData<Int>()




    }
}