package com.example.restuarantfinder.navigation

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object RestaurantListScreen : Screen("list_screen")
    object ProfileScreen : Screen("profile_screen")
    object RestaurantScreen : Screen("restaurant_screen")
    object SignUpScreen : Screen("signup_screen")
    object MenuScreen : Screen("menu_screen/{title}")
    object ReservationScreen : Screen("reservation_screen")
}
