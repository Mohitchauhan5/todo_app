package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.notes.bottomsheetfragment.BottomSheetFragment
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.helper.NewHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        NewHelper.imageStatus.observe(this){

            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            transaction.replace(R.id.fragment_container,MainFragment(),"MyFragment")
            transaction.commit()

        }




      loadFragment(MainFragment())




    }

    private fun loadFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment,"MyFragment")

        transaction.commit()
    }

}