package com.OOP.FeslenderApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date


class festival_list : Fragment() {
    lateinit var fesdata: ArrayList<FesData>
    lateinit var adapter: FesAdapter
    lateinit var myRef: DatabaseReference

    lateinit var clickResult: String
    lateinit var searchResult: String

    val database =
        Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")


    var binding: FragmentFestivalListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clickResult = it.getString("click") ?: "festival"
            searchResult = it.getString("search") ?: ""
            Log.e("searchResult", searchResult)
            Log.e("click:", clickResult)
            myRef = if (searchResult != "") database.getReference() else when(clickResult) {
                "match" -> database.getReference("match")
                "festival" -> database.getReference("festival")
                "concert" -> database.getReference("concert")
                else -> database.getReference()
            }
            Log.e("myRef", myRef.toString())
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
            @SuppressLint("SimpleDateFormat")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (searchResult != " ") {
                    for (data in snapshot.children) {
                        for (ds in data.children) {
                            val searchName =
                                if (data.key == "match") {
                                    "${ds.child("team1").value as String} vs ${
                                        ds.child("team2").value as String
                                    }"
                                } else {
                                    ds.child("name").value as String
                                }

                            if (searchName.contains(searchResult)) {
                                val myfesData = FesData().apply {
                                    if (data.key == "match") {
                                        name =
                                            "${ds.child("team1").value as String} vs ${ds.child("team2").value as String}"
                                        var pos = ds.child("pos1").value as String
                                        poster = pos.replace(
                                            "gs://feslender-kotlin-70d17.appspot.com/",
                                            ""
                                        )
                                    } else {
                                        name = ds.child("name").value as String
                                        poster = ds.child("pos").value as String
                                    }
                                    date = ds.child("date").value as String
                                    end_date = date
                                    location = ds.child("location").value as String
                                }
                                fesdata.add(myfesData)
                            }

                        }
                    }
                    if (fesdata.size == 0){
                        Toast.makeText(context,"검색 결과가 없습니다",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (clickResult == "soon") {
                        val nowDate = SimpleDateFormat("yyyy년 MM월dd일").format(Date())

                        for (data in snapshot.children) {
                            for (ds in data.children) {
                                var date = ds.child("date").value as String
                                val cmp = nowDate.compareTo(date)

                                if (cmp < 0) {
                                    val myfesData = FesData().apply {
                                        if (data.key == "match") {
                                            name =
                                                "${ds.child("team1").value as String} vs ${
                                                    ds.child(
                                                        "team2"
                                                    ).value as String
                                                }"
                                            var pos = ds.child("pos1").value as String
                                            poster = pos.replace(
                                                "gs://feslender-kotlin-70d17.appspot.com/",
                                                ""
                                            )
                                        } else {
                                            name = ds.child("name").value as String
                                            poster = ds.child("pos").value as String
                                        }
                                        date = ds.child("date").value as String
                                        end_date = date
                                        location = ds.child("location").value as String
                                    }
                                    fesdata.add(myfesData)
                                }
                            }

                        }
                    } else if (clickResult == "match") {
                        for (ds in snapshot.children) {
                            var pos = ds.child("pos1").value as String
                            pos = pos.replace("gs://feslender-kotlin-70d17.appspot.com/", "")

                            val myfesData = FesData().apply {
                                name =
                                    "${ds.child("team1").value as String} vs ${ds.child("team2").value as String}"
                                date = ds.child("date").value as String
                                end_date = date
                                location = ds.child("location").value as String
                                poster = pos
                            }
                            fesdata.add(myfesData)
                        }
                    } else {
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
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return binding?.root
    }
}