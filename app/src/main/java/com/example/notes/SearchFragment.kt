package com.example.notes

import android.os.Bundle
import android.text.TextUtils

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.core.widget.addTextChangedListener

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.adapter.SearchAdapter
import com.example.notes.databinding.FragmentSearchBinding
import com.example.notes.helper.NotesDatabase
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository



class SearchFragment : Fragment() {

    private var _listData = mutableListOf<Notes>()

    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter : SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentSearchBinding.inflate(layoutInflater)


        if(activity!=null){
            adapter = SearchAdapter(requireActivity().supportFragmentManager)
        }



        binding.recyclerViewSearch.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)


        val dao = NotesDatabase.getInstance(requireContext())

        binding.recyclerViewSearch.adapter = adapter



         val repository = NotesRepository(dao)



        repository.notesLiveData.observe(requireActivity()){
            for(i in it){
                _listData.add(Notes(i.id,i.title,i.note,i.date))
            }
            addFirstTime()
        }





        binding.edtSearch.addTextChangedListener {
            filter(it.toString())
        }




        return binding.root
    }



    private fun addFirstTime(){
        val filterList =  mutableListOf<Notes>()
        for(i in _listData){
                filterList.add(Notes(i.id,i.title,i.note,i.date))

        }
        adapter.insertListData(filterList)
    }
    private fun filter(text : String){
        val filterList =  mutableListOf<Notes>()
        for(i in _listData){
            if(i.title.startsWith(text,true) ){
               filterList.add(Notes(i.id,i.title,i.note,i.date))
            }
        }
        if(!TextUtils.isEmpty(text)){

            adapter.insertListData(filterList)

        }
    }

}