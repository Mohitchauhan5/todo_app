package com.example.notes.bottomsheetfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.notes.AddNotesFragment
import com.example.notes.CheckListFragment
import com.example.notes.R
import com.example.notes.helper.AddCheck
import com.example.notes.helper.NewHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.custom_bottom,container,false)
        val textNote : TextView= view.findViewById(R.id.text_my_notes)
        val textCheckList : TextView= view.findViewById(R.id.text_checklist)
        val transaction = parentFragmentManager.beginTransaction()


        textNote.setOnClickListener {

            val addNotesFragment = AddNotesFragment()
            val bundle = Bundle()

            bundle.putString("AddStatus","NoteAvailable")
            addNotesFragment.arguments = bundle


            transaction.replace(R.id.fragment_container,addNotesFragment)
            transaction.commit()
            dismiss()

        }

        textCheckList.setOnClickListener {


            NewHelper.Makelist.clear()
            val addNotesFragment = CheckListFragment()
            val bundle = Bundle()

            bundle.putString("AddStatus","CheckListAvailable")
            addNotesFragment.arguments = bundle

            transaction.replace(R.id.fragment_container,addNotesFragment)
            transaction.commit()

            dismiss()

        }

        return view
    }


}