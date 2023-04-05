package com.example.restuarantfinder

sealed class Screen(val route: String){
    object HomeScreen : Screen ("home_screen")
    object RestaurantListScreen : Screen ("list_screen")
}
