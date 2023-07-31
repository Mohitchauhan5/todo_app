package com.example.notes.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.AddNotesFragment
import com.example.notes.MainCheckListFragment
import com.example.notes.MainNotesFragment
import com.example.notes.SearchFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1-> MainCheckListFragment()
            else -> MainNotesFragment()
        }

    }
}