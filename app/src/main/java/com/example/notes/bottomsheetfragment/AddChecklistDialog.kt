package com.example.notes.bottomsheetfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.notes.AddNotesFragment
import com.example.notes.R
import com.example.notes.helper.AddCheck
import com.example.notes.helper.NewHelper
import com.example.notes.model.Check

class AddChecklistDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.custom_dialog,container,false)

        val edt = view.findViewById<EditText>(R.id.edt_my_notes_dialog)
        val btn = view.findViewById<TextView>(R.id.btn_checklist_dialog)

        btn.setOnClickListener {
            NewHelper.Makelist.add( Check("no",edt.text.toString()))
            NewHelper.checkStatus.value = 1
            dismiss()
        }


        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}