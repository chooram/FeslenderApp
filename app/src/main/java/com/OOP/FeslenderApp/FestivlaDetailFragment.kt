package com.OOP.FeslenderApp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalDetailBinding
import com.OOP.FeslenderApp.databinding.FragmentFestivalListBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class FestivlaDetailFragment : Fragment() {

    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var clickItem: String

    var binding: FragmentFestivalDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFestivalDetailBinding.inflate(inflater)
        return binding?.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database =
            Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")

        arguments?.let {
            clickItem = it.getString("clickItem") ?: ""
            myRef = database.getReference()
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (data in snapshot.children){
                    val  chkfestival = data.key == "festival"

                    for (ds in data.children){

                        if (ds.child("name").value == clickItem){
                            if (chkfestival){
                                val name = clickItem
                                val poster = ds.child("pos").value as String
                                val date = ds.child("date").value as String
                                val location = ds.child("location").value as String
                                val end_date = ds.child("end_date").value as String
                                val phone_number = ds.child("phone_number").value as String
                                val home_site = ds.child("home_site").value as String
                                val ticket_site = ds.child("ticket_site").value as String


                                binding?.textView2?.text = name
                                binding?.txtLocation?.text = location
                                binding?.txtDate?.text = date
                                binding?.txtHomepage?.text = home_site
                                binding?.txtTicket?.text = ticket_site
                                binding?.txtPhone?.text = phone_number

                                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                                val storageRef: StorageReference = storage.reference
                                binding?.let{
                                    storageRef.child(poster).downloadUrl.addOnSuccessListener { uri->
                                        Glide.with(it.root).load(uri).into(it.imgPoster)
                                    }.addOnFailureListener{
                                    }
                                }
                            }
                        }
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        }
        )
    }

}

