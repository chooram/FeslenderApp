package com.OOP.FeslenderApp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalDetailBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class FestivlaDetailFragment : Fragment() {

    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase

    var binding: FragmentFestivalDetailBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database =
            Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")

        arguments?.let {
            clickResult = it.getString("click") ?: ""
            searchResult = it.getString("search") ?: ""
            myD = database.getReference()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fesdata.observe(viewLifecycleOwner){
            binding?.recFestival?.adapter?.notifyDataSetChanged()
        }
        binding?.recFestival?.layoutManager = LinearLayoutManager(context)
        binding?.recFestival?.adapter = FesAdapter(viewModel.fesdata)

        myD.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (data in snapshot.children){

                    val  chkMatch = data.key == "match"
                    Log.e("check Match",chkMatch.toString())

                    val  chkConcert = data.key == "concert"
                    Log.e("check Concert",chkConcert.toString())

                    val  chkFestival = data.key == "festival"
                    Log.e("check Festival",chkFestival.toString())

                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        }
        )
    }

}

