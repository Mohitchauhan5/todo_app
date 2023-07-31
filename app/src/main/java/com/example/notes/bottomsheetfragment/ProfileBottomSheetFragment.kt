package com.example.notes.bottomsheetfragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.notes.MainFragment
import com.example.notes.R
import com.example.notes.helper.NewHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheetFragment : BottomSheetDialogFragment() {
    private var status = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.custom_layout_profile, container, false)

        val sh = activity?.getSharedPreferences("profileInfo",MODE_PRIVATE)
        val edit = sh?.edit()
        val imageData = sh?.getString("imageData",null)
        val profileName = sh?.getString("profileName",null)

        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)
        val bgImage1 = view.findViewById<FrameLayout>(R.id.bg_image1)
        val bgImage2 = view.findViewById<FrameLayout>(R.id.bg_image2)
        val button = view.findViewById<Button>(R.id.profile_info_save_button)
        val editText = view.findViewById<EditText>(R.id.edt_profile_name)


        if(imageData == null)
            bgImage1.visibility = View.VISIBLE
        else{
            if(imageData == "image1")
                bgImage1.visibility = View.VISIBLE
            else{
                bgImage2.visibility = View.VISIBLE
            }
        }


        image1.setOnClickListener {
            bgImage1.visibility = View.VISIBLE
            bgImage2.visibility = View.GONE
            status = 0

        }

        image2.setOnClickListener {
            bgImage1.visibility = View.GONE
            bgImage2.visibility = View.VISIBLE
            status = 1

        }


        button.setOnClickListener {
            val newName = editText.text.toString()
            if(TextUtils.isEmpty(newName)){
                if(status==0){
                    edit?.putString("imageData","image1")
                    edit?.apply()

                    NewHelper.imageStatus.value = 1

                    dismiss()



                }else{
                    edit?.putString("imageData","image2")
                    edit?.apply()


                    NewHelper.imageStatus.value = 1
                    dismiss()

                }

            }else{
                if(status==0){
                    edit?.putString("profileName",newName)
                    edit?.putString("imageData","image1")
                    edit?.apply()



                    NewHelper.imageStatus.value = 1
                    dismiss()


                }else{
                    edit?.putString("profileName",newName)
                    edit?.putString("imageData","image2")
                    edit?.apply()

                    NewHelper.imageStatus.value = 1
                    dismiss()

                }
            }

        }




        return view
    }

}