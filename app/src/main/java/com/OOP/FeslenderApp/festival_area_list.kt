package com.OOP.FeslenderApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalAreaListBinding
import com.OOP.FeslenderApp.databinding.FragmentFestivalListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class festival_area_list : Fragment() {
    lateinit var fesdata: ArrayList<FesData>
    lateinit var adapter: FesAdapter
    lateinit var myRef: DatabaseReference

    lateinit var clickResult : String

    val database =
        Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")
    var binding: FragmentFestivalAreaListBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myRef = database.getReference()

        arguments?.let{
            clickResult = it.getString("click_area") ?: "서울"
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFestivalAreaListBinding.inflate(inflater)
        fesdata = ArrayList()
        adapter = FesAdapter(fesdata)
        binding?.recFestivalArea?.layoutManager = LinearLayoutManager(context)
        binding?.recFestivalArea?.adapter = adapter

        binding?.btnLoca?.text = clickResult

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    for (ds in data.children) {
                        if ((ds.child("area").value as String) == clickResult) {
                            Log.e("Area Check",ds.child("area").value as String)
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
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnLoca?.setOnClickListener {
            popup_window()
        }
    }


    private fun popup_window() {
        val popupMenu = PopupMenu(context, view, Gravity.END, 30, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_seoul -> {
                    clickResult = "서울"
                    val bundle = bundleOf("click_area" to clickResult)
                    findNavController().navigate(R.id.action_festival_area_list_self,bundle)
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_busan -> {
                    clickResult = "부산"
                    val bundle = bundleOf("click_area" to clickResult)
                    findNavController().navigate(R.id.action_festival_area_list_self,bundle)
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_gyeonggi -> {
                    clickResult = "경기"
                    val bundle = bundleOf("click_area" to clickResult)
                    findNavController().navigate(R.id.action_festival_area_list_self,bundle)
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_incheon -> {
                    clickResult = "인천"
                    val bundle = bundleOf("click_area" to clickResult)
                    findNavController().navigate(R.id.action_festival_area_list_self,bundle)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }

        }
    }

}