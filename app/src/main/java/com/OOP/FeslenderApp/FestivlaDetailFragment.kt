package com.OOP.FeslenderApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.OOP.FeslenderApp.databinding.FragmentFestivalDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FestivlaDetailFragment : Fragment() {

    val database =
        Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")


    var binding: FragmentFestivalDetailBinding? = null

}
