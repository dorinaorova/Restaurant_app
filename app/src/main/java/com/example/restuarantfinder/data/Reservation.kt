package com.example.restuarantfinder.data

data class Reservation(
    var id: Long,
    var restaurant: Restaurant,
    var user: User,
    var time: Long,
    var people: Int
)
