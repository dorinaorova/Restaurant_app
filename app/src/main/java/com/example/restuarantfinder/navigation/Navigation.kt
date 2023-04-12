package com.example.restuarantfinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.restuarantfinder.screen.HomeScreen
import com.example.restuarantfinder.screen.ProfileScreen
import com.example.restuarantfinder.screen.RestaurantListScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController =navController)
        }
        composable(route = Screen.RestaurantListScreen.route){
            RestaurantListScreen(navController =navController)
        }
        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
    }
}
