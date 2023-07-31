package com.example.notes.helper


import androidx.lifecycle.MutableLiveData

class SearchHelper {

    companion object{
        var searchLiveData : MutableLiveData<String> = MutableLiveData()
    }
}