package com.example.notes


import android.os.Build
import android.os.Bundle
import android.text.TextUtils

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi

import com.example.notes.adapter.CheckNotesAdapter

import com.example.notes.databinding.FragmentAddNotesBinding

import com.example.notes.helper.ChangePosition
import com.example.notes.helper.NotesDatabase
import com.example.notes.helper.PositionRecycler

import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddNotesFragment : Fragment() {

    private lateinit var binding : FragmentAddNotesBinding
    private var updateStatus = 0

    private lateinit var checkAdapter : CheckNotesAdapter

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentAddNotesBinding.inflate(layoutInflater)

        checkAdapter = CheckNotesAdapter()








        val titleData = arguments?.getString("title")
        val idData = arguments?.getInt("id")
        val noteData = arguments?.getString("note")
        val dateData = arguments?.getString("date")
        if(titleData!=null && noteData!=null){
            binding.edtTitle.setText(titleData)
            binding.edtDescription.setText(noteData)
            binding.edtStatus.setColorFilter(R.color.image_grey,android.graphics.PorterDuff.Mode.MULTIPLY)
            PositionRecycler.editStatus = 0
           // binding.edtDescription.isFocusable = false
            updateStatus = 1


        }else{
            PositionRecycler.editStatus = 1
        }



        binding.edtStatus.setOnClickListener {
            if(PositionRecycler.editStatus==0){
                Toast.makeText(context,"EditMode Enabled",Toast.LENGTH_SHORT).show()
                binding.edtStatus.colorFilter = null
                binding.edtDescription.isFocusable = true
                binding.edtDescription.isFocusableInTouchMode = true
                PositionRecycler.editStatus = 1
            }else{
                Toast.makeText(context,"EditMode Disabled",Toast.LENGTH_SHORT).show()
                binding.edtStatus.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                binding.edtDescription.isFocusable = false

                PositionRecycler.editStatus = 0
            }
        }

        val dao = context?.let { NotesDatabase.getInstance(it) }

        val repository = dao?.let { NotesRepository(it) }













        binding.btnSave.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val note = binding.edtDescription.text.toString()
            if(TextUtils.isEmpty(title)){
                binding.edtTitle.error = "Title Cannot be Empty"
                binding.edtTitle.requestFocus()

            }

            else if(TextUtils.isEmpty(note)){
                binding.edtDescription.error = "Description Cannot be Empty"
                binding.edtDescription.requestFocus()

            }


            else{

                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)
                if(updateStatus==1){
                    ChangePosition.status.value = 1

                    GlobalScope.launch(Dispatchers.IO) {
                        if(titleData!=null && noteData!=null && idData!=null && dateData!=null){

                            repository?.updateNotes(Notes(idData,title, note,formatted))

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
                    ChangePosition.status.value = 1

                    GlobalScope.launch(Dispatchers.IO) {
                        repository?.insertNotes(Notes(0,title, note,formatted))

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



        //click on Image Back Button
        binding.imageBackAddNotes.setOnClickListener {
            onBackImage()
        }


        onBackPressed()

        return binding.root
    }



    private fun onBackImage(){
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true)
//            {
//                override fun handleOnBackPressed() {
//                    // Leave empty do disable back press or
//                    // write your code which you want
//                }
//            }
//        requireActivity().onBackPressedDispatcher.addCallback(
//            requireActivity(),
//            callback
//        )
            // Your logic here




        val transaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right)
        transaction.replace(R.id.fragment_container,MainFragment())

        transaction.commit()
       // activity?.onBackPressed()

//        val transaction = parentFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container,MainFragment())
//      //  transaction.addToBackStack(null)
//        transaction.commit()
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