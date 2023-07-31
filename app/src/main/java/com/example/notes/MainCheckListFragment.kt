package com.example.notes

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.notes.adapter.CheckNotesMainAdapter

import com.example.notes.databinding.FragmentMainCheckListBinding
import com.example.notes.helper.ChangePosition
import com.example.notes.helper.NewHelper
import com.example.notes.helper.NotesDatabase
import com.example.notes.helper.PositionRecycler
import com.example.notes.helper.SearchHelper
import com.example.notes.mainviewmodel.MainViewModel
import com.example.notes.model.CheckNotes

import com.example.notes.repository.NotesRepository
import com.example.notes.viewmodelfactory.MainViewModelFactory


class MainCheckListFragment : Fragment() {


    private val listData = mutableListOf<CheckNotes>()
    private lateinit var binding : FragmentMainCheckListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       binding = FragmentMainCheckListBinding.inflate(layoutInflater)







        val checkAdapter = activity?.supportFragmentManager?.let { context?.let { it1 ->
            CheckNotesMainAdapter(it,
                it1
            )
        } }!!


        binding.recyclerViewMainCheck.layoutManager =LinearLayoutManager(context)


        val dao = NotesDatabase.getInstance(requireContext())




        binding.recyclerViewMainCheck.adapter = checkAdapter



        val repository = NotesRepository(dao)





        val viewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]





        viewModel.checkLiveData.observe(viewLifecycleOwner){
            listData.clear()

            for(i in it){
                listData.add(CheckNotes(i.id,i.title,i.list,i.date))
            }
            checkAdapter.insertListData(it)
            Log.d("MyTah",listData.size.toString())


            if(ChangePosition.position==-1){
                binding.recyclerViewMainCheck.scrollToPosition(checkAdapter.position -1 )

            }else{
                binding.recyclerViewMainCheck.scrollToPosition(ChangePosition.position -1 )

            }



            if(PositionRecycler.status == 1){

                if(checkAdapter!=null){
                    binding.recyclerViewMainCheck.scrollToPosition(checkAdapter.position -1 )
                    PositionRecycler.status = 0
                }

            }




        }






        ChangePosition.status.observe(requireActivity()){
            if(it==2){
                if(ChangePosition.position==-1){
                    binding.recyclerViewMainCheck.scrollToPosition(listData.size -1)

                }else{
                    binding.recyclerViewMainCheck.scrollToPosition(ChangePosition.position)

                }

            }
        }

        SearchHelper.searchLiveData.observe(viewLifecycleOwner){
            if(it!=""){
                Log.d("SearchItem",it)
                if (checkAdapter != null) {
                    filter(it,checkAdapter)
                    Log.d("SearchItem",it+"vikcy")
                }
            }else{
                viewModel.checkLiveData.observe(viewLifecycleOwner){ newIt ->
                    listData.clear()


                    for(i in newIt){
                        listData.add(CheckNotes(i.id,i.title,i.list,i.date))
                    }
                    checkAdapter.insertListData(newIt)



                    if(PositionRecycler.status == 1){

                        if(checkAdapter!=null){
                            binding.recyclerViewMainCheck.scrollToPosition(checkAdapter.position -1 )
                            PositionRecycler.status = 0
                        }

                    }






                }
            }

        }

        return binding.root
    }


    private fun filter(text : String,adapter : CheckNotesMainAdapter){
        val filterList =  mutableListOf<CheckNotes>()
        for(i in listData){
            if(i.title.startsWith(text,true) ){
                filterList.add(CheckNotes(i.id,i.title,i.list,i.date))
            }
        }
        if(!TextUtils.isEmpty(text)){

            adapter.insertListData(filterList)

        }
    }


}