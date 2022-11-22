package com.OOP.FeslenderApp

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class festival_list : Fragment() {
    lateinit var fesdata: ArrayList<FesData>
    lateinit var adapter: FesAdapter
    lateinit var myRef: DatabaseReference

    private var clickResult: String = "festival"

    val database =
        Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")


    var binding: FragmentFestivalListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clickResult = it.getString("click") ?: "festival"
            Log.e("click:", clickResult)
            myRef = database.getReference(clickResult)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFestivalListBinding.inflate(inflater)
        fesdata = ArrayList()
        adapter = FesAdapter(fesdata)
        binding?.recFestival?.layoutManager = LinearLayoutManager(context)
        binding?.recFestival?.adapter = adapter


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (clickResult == "match") {
                    for (ds in snapshot.children){
                        var pos = ds.child("pos1").value as String
                        pos = pos.replace("gs://feslender-kotlin-70d17.appspot.com/","")

                        val myfesData = FesData().apply{
                            name = "${ds.child("team1").value as String} vs ${ds.child("team2").value as String}"
                            date = ds.child("date").value as String
                            end_date = date
                            location = ds.child("location").value as String
                            poster = pos
                        }
                        fesdata.add(myfesData)
                    }
                }
                else{
                    for (ds in snapshot.children) {
                        val myfesData = FesData().apply {
                            name = ds.child("name").value as String
                            date = ds.child("date").value as String
                            if (ds.child("end_data").value != null) {
                                end_date = ds.child("end_date").value as String
                            } else {
                                end_date = date
                            }
                            location = ds.child("location").value as String
                            poster = ds.child("pos").value as String
                        }
                        fesdata.add(myfesData)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return binding?.root
    }
}