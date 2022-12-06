package com.OOP.FeslenderApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot

data class FesData(
    var name: String,
    var date: String,
    var location: String,
    var end_date: String,
    var poster: String)

data class DateEvent(
    val date: String,
    val event: String,
    var color: String,
    val location: String,
    val image: String)

data class DateEvents(
    val date: String,
    var events: ArrayList<String>,
    var colors: ArrayList<String>,
    var locations: ArrayList<String>,
    var times:ArrayList<String>,
    var images: ArrayList<String>
)

class FestivalViewModel: ViewModel(){
    private val repository = FestivalRepository()
    private val _fesdata = MutableLiveData<ArrayList<FesData>>()
    private val _areaSelect = MutableLiveData<String>()
    private val _dateevent = MutableLiveData<ArrayList<DateEvent>>()
    private val fesList = ArrayList<FesData>()
    private val eventList = ArrayList<DateEvent>()

    val fesdata: LiveData<ArrayList<FesData>> = _fesdata
    val areaSelect : LiveData<String> = _areaSelect
    val dateevent : LiveData<ArrayList<DateEvent>> = _dateevent

    fun addList(ds : DataSnapshot, chkMatch : Boolean){
        fesList.add(repository.readData(ds,chkMatch))
        _fesdata.value = fesList
    }

    fun clearList(){
        _fesdata.value?.clear()
    }

    fun addEventList(ds: DataSnapshot){
        eventList.add(repository.readEventData(ds))
        _dateevent.value = eventList
    }

    fun setArea(area: String){
        _areaSelect.value = area
    }

}