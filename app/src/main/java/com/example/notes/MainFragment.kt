package com.example.notes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.example.notes.adapter.CheckNotesMainAdapter
import com.example.notes.adapter.ViewPagerAdapter
import com.example.notes.bottomsheetfragment.BottomSheetFragment
import com.example.notes.bottomsheetfragment.ProfileBottomSheetFragment
import com.example.notes.databinding.FragmentMainBinding
import com.example.notes.helper.ChangePosition
import com.example.notes.helper.NewHelper
import com.example.notes.helper.SearchHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.DelicateCoroutinesApi


class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private lateinit var checkAdapter : CheckNotesMainAdapter
    private  val profileBottom = ProfileBottomSheetFragment()

    private val rotateOpen by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_open) }
    private val rotateClose by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_close) }
    private val toBottom by lazy { AnimationUtils.loadAnimation(context,R.anim.to_bottom) }
    private val fromBottom by lazy { AnimationUtils.loadAnimation(context,R.anim.from_bottom) }
    private var clicked = false

    private lateinit var nameData : String



    private lateinit var viewadapter : ViewPagerAdapter
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val transaction = parentFragmentManager.beginTransaction()



        //  binding.imageset.setBackgroundResource(R.drawable.image2)

      //  binding.imageset.setImageResource(R.drawable.save)
        //binding.imageset.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.save) });
        //binding.profileImage.setImageResource(R.drawable.profile_image)

        //set Image and Profile Name









        binding = FragmentMainBinding.inflate(layoutInflater)





                val sh = context?.getSharedPreferences("profileInfo", Context.MODE_PRIVATE)
         val imageData = sh?.getString("imageData",null)
        val profileName = sh?.getString("profileName",null)
        if(imageData!=null && profileName!=null){
            Log.d("Sh",imageData)
            if(imageData == "image1")
                binding.profileImage.setImageResource(R.drawable.profile_image)
            else
                binding.profileImage.setImageResource(R.drawable.image2)

            nameData  = "Hi, $profileName"

            binding.textProfileName.text = nameData
        }









        viewadapter = ViewPagerAdapter(childFragmentManager,lifecycle)
       binding.viewPager.adapter = viewadapter

        val bottomSheetFragment = BottomSheetFragment()



        ChangePosition.status.observe(requireActivity()){
            if(it==2){
                binding.viewPager.currentItem = 1
            }
        }

        binding.linearNote.setOnClickListener {
            val addNotesFragment = AddNotesFragment()
            val bundle = Bundle()

            bundle.putString("AddStatus","NoteAvailable")
            addNotesFragment.arguments = bundle

            transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            transaction.replace(R.id.fragment_container,addNotesFragment)
            transaction.addToBackStack(null)
            clicked = false
            transaction.commit()
        }

        binding.linearChecklist.setOnClickListener {

            NewHelper.Makelist.clear()
            val addNotesFragment = CheckListFragment()
            val bundle = Bundle()

            bundle.putString("AddStatus","CheckListAvailable")
            addNotesFragment.arguments = bundle

            transaction.addToBackStack(null)
            transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            transaction.replace(R.id.fragment_container,addNotesFragment)
            clicked = false
            transaction.commit()

        }



        binding.floating.setOnClickListener {
            onAddButtonCLicked()

           // bottomSheetFragment.show(parentFragmentManager,"null")

        }

        binding.edtSearch.addTextChangedListener {
            SearchHelper.searchLiveData.value = it.toString()
        }

        val tabList = listOf("Notes","Checklist")


        TabLayoutMediator(binding.tabLayout,binding.viewPager){myTab, position ->
            myTab.text = tabList[position]



        }.attach()



        binding.profileCardView.setOnClickListener {
            profileBottom()
        }


//        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrollStateChanged(state: Int) {
////                if(state==0)
////                    binding.edtSearch.hint = "Search Notes..."
////                if(state==1)
////                    binding.edtSearch.hint = "Search CheckNotes..."
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
////                if(positionOffset==0.0F)
////                    binding.edtSearch.hint = "Search Notes..."
////                if(positionOffset==1.0F)
////                    binding.edtSearch.hint = "Search CheckNotes..."
//
////                if(positionOffsetPixels==0)
////                    binding.edtSearch.hint = "Search Notes..."
////                if(positionOffsetPixels==1)
////                    binding.edtSearch.hint = "Search CheckNotes..."
////
//                if(position==0)
//                    binding.edtSearch.hint = "Search Notes..."
//                if(position==1)
//                    binding.edtSearch.hint = "Search CheckNotes..."
//
//            }
//
//
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
////                if(position==0)
////                    binding.edtSearch.hint = "Search Notes..."
////                if(position==1)
////                    binding.edtSearch.hint = "Search CheckNotes..."
//
//            }
//        })






        onBackPressed()

        return binding.root
    }

    private fun profileBottom(){
        profileBottom.show(childFragmentManager,"hi")

    }

    private fun onAddButtonCLicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked

    }

    private fun setAnimation(clicked : Boolean) {

        if(!clicked){
            binding.linearChecklist.startAnimation(fromBottom)
            binding.linearNote.startAnimation(fromBottom)
            binding.floating.startAnimation(rotateOpen)

        }else{
            binding.linearChecklist.startAnimation(toBottom)
            binding.linearNote.startAnimation(toBottom)
            binding.floating.startAnimation(rotateClose)
        }



    }

    private fun setVisibility(clicked : Boolean) {

        if(!clicked){
            binding.linearChecklist.visibility = View.VISIBLE
            binding.linearNote.visibility = View.VISIBLE
        }else{
            binding.linearChecklist.visibility = View.INVISIBLE
            binding.linearNote.visibility = View.INVISIBLE
        }


    }

    private fun onBackPressed(){

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {



                if(binding.viewPager.currentItem==1){
                    binding.viewPager.currentItem = 0
                }else{
                    activity?.finishAffinity()
                }
                // you can execute the logic here
            }
        })



    }



}


