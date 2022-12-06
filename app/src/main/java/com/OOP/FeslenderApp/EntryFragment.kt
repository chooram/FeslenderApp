package com.OOP.FeslenderApp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.OOP.FeslenderApp.databinding.FragmentEntryBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class EntryFragment : Fragment() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var selectedDate: LocalDate

    var list02 = mutableSetOf<FesData>()
    val items = arrayListOf<DateEvents>()

    var binding: FragmentEntryBinding ?= null

    val viewModel: FestivalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseDatabase =  Firebase.database("https://feslender-kotlin-70d17-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = firebaseDatabase.getReference("All")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryBinding.inflate(inflater)
        return binding?.root
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        viewModel.dateevents.observe(viewLifecycleOwner){
            binding?.rvProfile?.adapter?.notifyDataSetChanged()
        }
         */

        selectedDate = LocalDate.now()
        setMonthView()
        getData()
        addEvent(viewModel.dateevent)

        binding?.menuHam?.setOnClickListener{
            findNavController().navigate(R.id.action_entryFragment_to_festival_search)
        }

        binding?.btnPre?.setOnClickListener{
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()///
            addEvent(viewModel.dateevent)
        }

        binding?.btnNext?.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
            addEvent(viewModel.dateevent)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(){
        binding?.monthYearText?.text = monthYearFromDate(selectedDate)
        binding?.txtMonth?.text = monthFromDate(selectedDate)

        items.clear()

        for(i in dayInMonthArray(selectedDate)){
            items.add(DateEvents( i ,ArrayList<String>(), ArrayList<String>(),
                ArrayList<String>(),ArrayList<String>(),ArrayList<String>())
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy MM")

        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthFromDate(date: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern("MM월")

        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<String>{
        var dayList = ArrayList<String>()

        var yearMonth = YearMonth.from(date)

        var lastDay = yearMonth.lengthOfMonth()

        var firstDay = selectedDate.withDayOfMonth(1)

        var dayOfWeek = firstDay.dayOfWeek.value

        for(i in 1..41){
            if(i <= dayOfWeek || i> (lastDay+ dayOfWeek)){
                dayList.add("")
            }else{
                dayList.add((i-dayOfWeek).toString())
            }
        }
        return dayList
    }

    //getData는 한번만 하게 바꾸기. . .
    private fun getData(){

        databaseReference?.addValueEventListener(object: ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    viewModel.addEventList(ds)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ooooo","onCancelled: ${error.toException()}")
            }
        }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent(list: LiveData<ArrayList<DateEvent>>) {
        list.value?.let {
            for (i in it) {
            if (monthFromDate(selectedDate).substring(0, 2) == i.date.substring(6, 8)) {
                for (j in items) {
                    if(j.date != "") {
                        if (i.date.substring(10, 11) == "0") {
                            if (i.date.substring(11, 12) == j.date) {
                                j.events.add(i.event)
                                j.colors.add(i.color)
                                j.locations.add(i.location)
                                j.images.add(i.image)
                            }
                        } else {
                            if (i.date.substring(10, 12) == j.date) {
                                j.events.add(i.event)
                                j.colors.add(i.color)
                                j.locations.add(i.location)
                                j.images.add(i.image)
                            }
                        }
                    }
                }
            }

        }

        }


        binding?.rvCell?.apply {
            adapter = Adapter01().build(items)
            layoutManager = GridLayoutManager(context, 7)///
            setHasFixedSize(true)
        }


        list02.clear()
        for(i in items){
            var size = i.events.size

            if(size != 0 ){
                if(size==1){
                    list02.add(FesData(i.events.elementAt(0),"${monthYearFromDate(selectedDate)} ${i.date}일",i.locations.elementAt(0),"",i.images.elementAt(0)))
                }
                else{
                    for(k in 0 until size){
                        list02.add(FesData(i.events.elementAt(k),"${monthYearFromDate(selectedDate)} ${i.date}일",i.locations.elementAt(k),"",i.images.elementAt(k)))
                    }
                }
            }
        }

        binding?.rvProfile?.apply {
            adapter = AdapterBelowList01().build(ArrayList(list02))
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
}
}