package com.example.notes.helper

import androidx.lifecycle.MutableLiveData

class ChangePosition {

    companion object{
        var status =  MutableLiveData<Int>()
        var position = 0

    }
}