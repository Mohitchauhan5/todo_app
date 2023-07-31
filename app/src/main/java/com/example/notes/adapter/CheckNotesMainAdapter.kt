package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

import com.example.notes.CheckListFragment
import com.example.notes.R
import com.example.notes.helper.AddCheck
import com.example.notes.helper.NewHelper
import com.example.notes.helper.NotesDatabase
import com.example.notes.model.Check
import com.example.notes.model.CheckNotes
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CheckNotesMainAdapter(private val fragmentManager: FragmentManager,private val context: Context) : RecyclerView.Adapter<CheckNotesMainAdapter.CheckHolder>() {

    private var list : List<CheckNotes> = listOf()
    var position = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun insertListData(list: List<CheckNotes>){
        this.list = list
        notifyDataSetChanged()
        position = list.size


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_item,parent,false)
        return CheckHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CheckHolder, position: Int) {
        var data = ""

        for(i in list[position].list){
            data  = "${data.plus(i.data)}\n"

        }

        holder.textTitle.text = list[position].title
        holder.textNote.text = data
        holder.textDate.text = list[position].date
        holder.linearLayout.setOnClickListener {

            NewHelper.Makelist.clear()

            for(i in list[position].list){
                NewHelper.Makelist.add(Check(i.status,i.data))
            }
            val addFragment = CheckListFragment()
            val bundle = Bundle()
            bundle.putString("title",list[position].title)
            bundle.putInt("id",list[position].id)
            bundle.putString("note",list[position].list.toString())
            AddCheck.list.clear()
            bundle.putString("date",list[position].date)
            addFragment.arguments = bundle
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            transaction.replace(R.id.fragment_container,addFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        holder.frameCLick.setOnClickListener {
            if(holder.delete.visibility == View.INVISIBLE){
                holder.delete.visibility = View.VISIBLE
                holder.delete.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in))
            }else{
                holder.delete.visibility = View.INVISIBLE
                holder.delete.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_out))
            }



        }

        holder.delete.setOnClickListener {
            val dao = NotesDatabase.getInstance(context)
            val repository = NotesRepository(dao)


            CoroutineScope(Dispatchers.IO).launch{
                repository.deleteCheckNote(CheckNotes( list[position].id,list[position].title,list[position].list,list[position].date))
            }

            holder.delete.visibility = View.INVISIBLE
            holder.delete.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_out))
        }

    }

    class CheckHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textTitle : TextView = itemView.findViewById(R.id.text_title)
        val textNote : TextView = itemView.findViewById(R.id.text_note)
        val textDate : TextView = itemView.findViewById(R.id.text_date)
        val linearLayout : LinearLayout = itemView.findViewById(R.id.linear_layout)

        val delete : Button = itemView.findViewById(R.id.button_delete)
        val frameCLick : FrameLayout = itemView.findViewById(R.id.frame_click)


    }
}