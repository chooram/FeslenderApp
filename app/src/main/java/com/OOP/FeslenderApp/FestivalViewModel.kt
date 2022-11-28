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

class FestivalViewModel: ViewModel(){
    private val repository = FestivalRepository()
    private val _fesdata = MutableLiveData<ArrayList<FesData>>()
    private val fesList = ArrayList<FesData>()
    private val _areaSelect = MutableLiveData<String>()

    val fesdata: LiveData<ArrayList<FesData>> = _fesdata
    val areaSelect : LiveData<String> = _areaSelect

    fun addList(ds : DataSnapshot, chkMatch : Boolean){
        fesList.add(repository.readData(ds,chkMatch))
        _fesdata.value = fesList
    }

    fun clearList(){
        _fesdata.value?.clear()
    }

    fun setArea(area: String){
        _areaSelect.value = area
    }

}