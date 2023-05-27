package com.example.restuarantfinder.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.restuarantfinder.screen.*

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController,
            startDestination = Screen.HomeScreen.route)
    {
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController =navController)
        }
        composable(route = Screen.RestaurantListScreen.route){
            RestaurantListScreen(navController =navController)
        }
        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.RestaurantScreen.route+"/{id}"){
            RestaurantScreen(navController = navController, id = it.arguments?.getString("id"))
        }
        composable(route = Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.MenuScreen.route+"/{title}"){
            Log.d("Arg", it.arguments?.getString("title").toString())
            MenuScreen(navController = navController, title = it.arguments?.getString("title").toString())
        }
        composable(route = Screen.ReservationScreen.route+"/{id}"){
            ReservationScreen(navController = navController, id = it.arguments?.getString("id"))
        }
    }
}
