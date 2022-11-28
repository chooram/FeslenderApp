package com.OOP.FeslenderApp

import android.util.Log
import androidx.fragment.app.viewModels
import com.google.firebase.database.DataSnapshot

class FestivalRepository {
    fun readData(ds : DataSnapshot, chkMatch : Boolean): FesData{
        return run{
            val name = if(chkMatch) "${ds.child("team1").value as String} vs ${ds.child("team2").value as String}" else ds.child("name").value as String
            val date = ds.child("date").value as String
            val end_date = ds.child("end_date").value ?: date
            val poster = if(chkMatch) ds.child("pos1").value as String else ds.child("pos").value as String
            val location = ds.child("location").value as String

            FesData(name,date,location,end_date.toString(),poster)
        }
    }
}