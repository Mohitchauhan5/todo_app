package com.example.notes.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView

import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView

import com.example.notes.R
import com.example.notes.helper.AddCheck
import com.example.notes.helper.NewHelper
import com.example.notes.model.Check

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CheckNotesAdapter : RecyclerView.Adapter<CheckNotesAdapter.CheckHolder>() {

    private var list : MutableList<Check> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun insertData(list: MutableList<Check>){
        this.list  = list
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_check_notes,parent,false)
        return CheckHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size

    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CheckHolder, position: Int) {

        holder.delete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                list.removeAt(position)
                NewHelper.Makelist = list
                Log.d("Position", NewHelper.Makelist.toString())
                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                }
            }

        }



      holder.data.text = list[position].data


        holder.checkNotes.setOnClickListener {
            if(holder.checkNotes.isChecked){
               list[position].status = "complete"
                NewHelper.Makelist[position].status = "complete"
                Log.d("ListData",AddCheck.list.toString())
                notifyDataSetChanged()
            }else{
                list[position].status = "no"
                NewHelper.Makelist[position].status = "no"
                Log.d("ListData",AddCheck.list.toString())
                notifyDataSetChanged()
            }
        }

        if(list[position].status == "complete"){
            holder.completeLine.visibility = View.VISIBLE
            holder.checkNotes.isChecked = true

        }

        if(list[position].status == "no"){
            holder.checkNotes.isChecked = false
            holder.completeLine.visibility = View.GONE

        }


    }


    class CheckHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val checkNotes : CheckBox = itemView.findViewById(R.id.checkbox)
        val data : TextView = itemView.findViewById(R.id.check_text)
        val completeLine : FrameLayout = itemView.findViewById(R.id.complete_line)
        val delete : ImageView = itemView.findViewById(R.id.image_delete_checklist)

    }


}