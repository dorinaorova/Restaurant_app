package com.example.restuarantfinder.data

data class Restaurant(
    var id: Long,
    var name: String,
    var address: String,
    var phone: String,
    var email: String,
    var open: Int,
    var close: Int,
    var menu_Food: List<MenuItem>?,
    var menu_Drink: List<MenuItem>?,
    var tables: Int
){
    constructor() : this(0, "","","","",0,0,null,null,0)
}