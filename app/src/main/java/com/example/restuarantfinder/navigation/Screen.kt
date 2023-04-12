package com.example.restuarantfinder.navigation

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object RestaurantListScreen : Screen("list_screen")
    object ProfileScreen : Screen("profile_screen")
}
