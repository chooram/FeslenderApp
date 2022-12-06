package com.OOP.FeslenderApp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentFestivalAreaListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FestivalAreaListFragment : Fragment() {
    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var clickResult: String

    var binding: FragmentFestivalAreaListBinding? = null
    val viewModel: FestivalViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArea("서울")
        database =
            Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")
        myRef = database.getReference()

        arguments?.let {
            clickResult = it.getString("click_area") ?: "서울"
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFestivalAreaListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fesdata.observe(viewLifecycleOwner) {
            binding?.recFestivalArea?.adapter?.notifyDataSetChanged()
        }
        binding?.recFestivalArea?.layoutManager = LinearLayoutManager(context)
        binding?.recFestivalArea?.adapter = FesAdapter(viewModel.fesdata)

        viewModel.areaSelect.observe(viewLifecycleOwner) {
            viewModel.clearList()
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        if (data.key != "All") {
                            val chkMatch = data.key == "match"
                            for (ds in data.children) {
                                if ((ds.child("area").value as String) == viewModel.areaSelect.value) {
                                    Log.e("viewModel:", viewModel.areaSelect.value.toString())
                                    viewModel.addList(ds, chkMatch)
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
            binding?.btnLoca?.text = viewModel.areaSelect.value
            binding?.recFestivalArea?.adapter?.notifyDataSetChanged()
        }
        binding?.btnLoca?.setOnClickListener {
            popupWindow()
        }
    }


    private fun popupWindow() {
        val popupMenu = PopupMenu(context, view, Gravity.END, 30, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_seoul -> {
                    viewModel.setArea("서울")
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_busan -> {
                    viewModel.setArea("부산")
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_gyeonggi -> {
                    viewModel.setArea("경기")
                    return@setOnMenuItemClickListener true


                }
                R.id.menu_incheon -> {
                    viewModel.setArea("인천")
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}