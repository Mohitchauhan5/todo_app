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
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.AddNotesFragment
import com.example.notes.R
import com.example.notes.helper.NotesDatabase
import com.example.notes.model.Check
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class MainAdapter(private val fragmentManager: FragmentManager,private val context: Context) : RecyclerView.Adapter<MainAdapter.Holder>() {

    private var list : List<Notes> = listOf()
    var position = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun insertListData(list: List<Notes>){
        this.list = list
       notifyDataSetChanged()
        position = list.size


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_item,parent,false)
        return Holder(view)

    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

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
                repository.deleteNote(Notes( list[position].id,list[position].title,list[position].note,list[position].date))
            }

            holder.delete.visibility = View.INVISIBLE
            holder.delete.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_out))


        }

    }

    class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textTitle : TextView = itemView.findViewById(R.id.text_title)
        val textNote : TextView = itemView.findViewById(R.id.text_note)
        val textDate : TextView = itemView.findViewById(R.id.text_date)
        val linearLayout : LinearLayout = itemView.findViewById(R.id.linear_layout)
        val delete : Button = itemView.findViewById(R.id.button_delete)
        val frameCLick : FrameLayout = itemView.findViewById(R.id.frame_click)


    }
}