package com.OOP.FeslenderApp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FestivalListFragment : Fragment() {
    lateinit var myRef: DatabaseReference
    lateinit var clickResult: String
    lateinit var searchResult: String
    lateinit var database: FirebaseDatabase

    val viewModel: FestivalViewModel by viewModels()
    var binding: FragmentFestivalListBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")

        arguments?.let {
            clickResult = it.getString("click") ?: ""
            searchResult = it.getString("search") ?: ""
            myRef = database.getReference()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFestivalListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fesdata.observe(viewLifecycleOwner){
            binding?.recFestival?.adapter?.notifyDataSetChanged()
        }
        binding?.recFestival?.layoutManager = LinearLayoutManager(context)
        binding?.recFestival?.adapter = FesAdapter(viewModel.fesdata)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (data in snapshot.children){
                    if(data.key != "All"){
                    if (clickResult != "" && clickResult != "soon") {
                        if (data.key != clickResult) continue
                    }
                    val  chkMatch = data.key == "match"

                    for (ds in data.children){
                        if(clickResult == "soon"){
                            val nowDate = "2022년 10월20일"
                            //val nowDate = SimpleDateFormat("yyyy년 MM월dd일").format(Date())
                            val date = ds.child("date").value as String

                            val cmp = nowDate.compareTo(date)

                            if (cmp < 0) {
                                viewModel.addList(ds,chkMatch)
                            }

                        }
                        else if (searchResult != ""){
                            val searchName =
                                if (chkMatch)
                                    "${ds.child("team1").value as String} vs ${
                                        ds.child("team2").value as String
                                    }"
                                 else
                                    ds.child("name").value as String

                            if (searchName.contains(searchResult)){
                                viewModel.addList(ds,chkMatch)
                            }

                        }
                        else viewModel.addList(ds, chkMatch)
                    }
                }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}