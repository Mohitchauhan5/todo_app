package com.example.notes

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.notes.adapter.MainAdapter

import com.example.notes.databinding.FragmentMainNotesBinding

import com.example.notes.helper.NotesDatabase
import com.example.notes.helper.PositionRecycler
import com.example.notes.helper.SearchHelper
import com.example.notes.mainviewmodel.MainViewModel
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import com.example.notes.viewmodelfactory.MainViewModelFactory


class MainNotesFragment : Fragment() {

    private val listData = mutableListOf<Notes>()

    private lateinit var binding : FragmentMainNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainNotesBinding.inflate(layoutInflater)




        val adapter = activity?.supportFragmentManager?.let {
            context?.let { it1 -> MainAdapter(it, it1) }
        }



        binding.recyclerViewMainNotes.layoutManager = LinearLayoutManager(context)

        val dao = NotesDatabase.getInstance(requireContext())






        binding.recyclerViewMainNotes.adapter = adapter

        val repository = NotesRepository(dao)



        val viewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]


        viewModel.notesLiveData.observe(viewLifecycleOwner){ newIt ->

            listData.clear()
            for(i in newIt){
                listData.add(Notes(i.id,i.title,i.note,i.date))
            }
            adapter?.insertListData(newIt)
            repository.list = newIt



            if(PositionRecycler.status == 1){

                if(adapter!=null){
                    binding.recyclerViewMainNotes.scrollToPosition(adapter.position -1 )
                    PositionRecycler.status = 0
                }

            }





        }


        SearchHelper.searchLiveData.observe(viewLifecycleOwner){
            if(it!=""){
                Log.d("SearchItem",it)
                if (adapter != null) {
                    filter(it,adapter)
                    Log.d("SearchItem",it+"vikcy")
                }
            }else{
                viewModel.notesLiveData.observe(viewLifecycleOwner){ newIt ->

                    listData.clear()

                    for(i in newIt){
                        listData.add(Notes(i.id,i.title,i.note,i.date))
                    }
                    adapter?.insertListData(newIt)
                    repository.list = newIt


                    if(PositionRecycler.status == 1){

                        if(adapter!=null){
                            binding.recyclerViewMainNotes.scrollToPosition(adapter.position -1 )
                            PositionRecycler.status = 0
                        }

                    }






                }
            }

        }



        return binding.root
    }


    private fun filter(text : String,adapter : MainAdapter){
        val filterList =  mutableListOf<Notes>()
        for(i in listData){
            if(i.title.startsWith(text,true) ){
                filterList.add(Notes(i.id,i.title,i.note,i.date))
            }
        }
        if(!TextUtils.isEmpty(text)){

            adapter.insertListData(filterList)

        }
    }

}