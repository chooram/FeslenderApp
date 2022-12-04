package com.OOP.FeslenderApp

data class DateEvents(
    val date: String,
    var events: ArrayList<String>,
    var colors: ArrayList<String>,//여기 MutableSet x수도...
    var locations: ArrayList<String>,
    var times:ArrayList<String>,
    var images: ArrayList<String>

)
