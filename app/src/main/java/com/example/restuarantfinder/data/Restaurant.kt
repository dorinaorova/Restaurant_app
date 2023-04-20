package com.example.restuarantfinder.data

data class Restaurant(
    var name: String,
    var address: String,
    var phone: String,
    var email: String,
    var open: String,
    var menu_Food: List<MenuItem>?,
    var menu_Drink: List<MenuItem>?
)