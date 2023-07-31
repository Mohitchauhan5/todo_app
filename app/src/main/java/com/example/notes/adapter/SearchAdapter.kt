package com.example.notes.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.example.notes.AddNotesFragment
import com.example.notes.R
import com.example.notes.model.Notes


class SearchAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    private var  colorStatus = 0

    private var list : List<Notes> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun insertListData(list: List<Notes>){
        this.list = list




       notifyDataSetChanged()



    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_item,parent,false)
        return SearchHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        colorStatus = when(colorStatus){
            0->{
                holder.linearLayout.setBackgroundResource(R.drawable.light_blue_bg)
                1
            }

            1->{
                holder.linearLayout.setBackgroundResource(R.drawable.light_pink_bg)
                2
            }

            else ->{
                holder.linearLayout.setBackgroundResource(R.drawable.light_yellow_bg)
                0
            }
        }

            holder.textTitle.text = list[position].title
            holder.textNote.text = list[position].note
            holder.textDate.text = list[position].date
        holder.linearLayout.setOnClickListener {
            val addFragment = AddNotesFragment()
            val bundle = Bundle()
            bundle.putString("title",list[position].title)
            bundle.putInt("id",list[position].id)
            bundle.putString("note",list[position].note)
            bundle.putString("date",list[position].date)
            addFragment.arguments = bundle
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,addFragment)
            transaction.commit()
        }


    }

    class SearchHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textTitle : TextView = itemView.findViewById(R.id.text_title)
        val textNote : TextView = itemView.findViewById(R.id.text_note)
        val textDate : TextView = itemView.findViewById(R.id.text_date)
        val linearLayout : LinearLayout = itemView.findViewById(R.id.linear_layout)


    }
}