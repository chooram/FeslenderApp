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
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class festival_list : Fragment() {
    lateinit var fesdata: ArrayList<FesData>
    lateinit var adapter: FesAdapter

    val database =
        Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("festival")

    var binding: FragmentFestivalListBinding? = null

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
                for (ds in snapshot.children) {
                    val myfesData = FesData().apply{
                        name = ds.child("name").value as String
                        date = ds.child("date").value as String
                        end_date = ds.child("end_date").value as String
                        location = ds.child("location").value as String
                        poster = ds.child("pos").value as String
                    }
                    fesdata.add(myfesData)
                    /*
                    val name: String = ds.child("name").value as String
                    val date: String = ds.child("date").value as String
                    val endDate: String = ds.child("end_date").value as String
                    val location: String = ds.child("location").value as String
                    val poster: String = ds.child("pos").value as String

                    val myfesData = FesData(name, date, location, endDate, poster)
                    fesdata.add(myfesData)

                     */
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return binding?.root
    }
}