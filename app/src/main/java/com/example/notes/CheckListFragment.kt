package com.example.notes

import android.os.Build
import android.os.Bundle
import android.text.TextUtils

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.notes.adapter.CheckNotesAdapter
import com.example.notes.bottomsheetfragment.AddChecklistDialog

import com.example.notes.databinding.FragmentCheckListBinding

import com.example.notes.helper.ChangePosition
import com.example.notes.helper.NewHelper
import com.example.notes.helper.NotesDatabase
import com.example.notes.helper.PositionRecycler

import com.example.notes.model.CheckNotes
import com.example.notes.repository.NotesRepository


import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class CheckListFragment : Fragment() {


    private lateinit var binding : FragmentCheckListBinding
    private lateinit var checkAdapter : CheckNotesAdapter
    private var updateStatus = 0
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentCheckListBinding.inflate(layoutInflater)



        checkAdapter = CheckNotesAdapter()


        val titleData = arguments?.getString("title")
        val idData = arguments?.getInt("id")
        val noteData = arguments?.getString("note")
        val dateData = arguments?.getString("date")


        if(titleData!=null && noteData!=null){
            binding.edtTitle.setText(titleData)

            updateStatus = 1

        }

        binding.checkListRecycler.layoutManager = LinearLayoutManager(context)
        binding.checkListRecycler.adapter = checkAdapter






        val dao = context?.let { NotesDatabase.getInstance(it) }

        val repository = dao?.let { NotesRepository(it) }



        if(idData!=null){

            checkAdapter.insertData(NewHelper.Makelist)

        }

        NewHelper.checkStatus.observe(requireActivity()){
            checkAdapter.insertData(NewHelper.Makelist)
        }






        binding.btnSave.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            if(TextUtils.isEmpty(title)){
                binding.edtTitle.error = "Title Cannot be Empty"
                binding.edtTitle.requestFocus()

            }



            else{

                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)


                    if(updateStatus==1){
                        ChangePosition.status.value = 2
                        if(idData!=null){
                            ChangePosition.position = idData
                        }
                    GlobalScope.launch(Dispatchers.IO) {
                        if(titleData!=null && noteData!=null && idData!=null && dateData!=null){



                            repository?.updateCheckNotes(CheckNotes(idData,title,NewHelper.Makelist,formatted))

                            withContext(Dispatchers.Main){
                                PositionRecycler.status = 1
                                val beginTransaction = parentFragmentManager.beginTransaction()
                                beginTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                                beginTransaction.replace(R.id.fragment_container,MainFragment())
                                beginTransaction.commit()

                            }

                        }

                    }
                }

                else{

                        ChangePosition.status.value = 2
                        ChangePosition.position = -1
                        GlobalScope.launch(Dispatchers.IO) {
                            repository?.insertCheckNotes(CheckNotes(0,title,NewHelper.Makelist,formatted))
                           // ChangePosition.status.value = 2
                            withContext(Dispatchers.Main){
                                PositionRecycler.status = 1
                                val beginTransaction = parentFragmentManager.beginTransaction()
                                beginTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                                beginTransaction.replace(R.id.fragment_container,MainFragment())
                                beginTransaction.commit()

                            }
                        }
                    }






            }
        }

        binding.addChecklistFloating.setOnClickListener {
            floatingButton()
        }

        binding.imageBack.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right)
            transaction.replace(R.id.fragment_container,MainFragment())

            transaction.commit()
        }

        onBackPressed()
       return binding.root
    }

    private fun floatingButton(){

        val dialog = AddChecklistDialog()

        dialog.show(parentFragmentManager,"hi")

    }

    private fun onBackPressed(){

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right)
                transaction.replace(R.id.fragment_container,MainFragment())

                transaction.commit()
                // you can execute the logic here
            }
        })



    }


}