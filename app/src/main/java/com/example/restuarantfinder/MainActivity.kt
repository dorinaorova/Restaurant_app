package com.example.restuarantfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.navigation.Navigation
import com.example.restuarantfinder.ui.theme.RestuarantFinderTheme

class MainActivity : ComponentActivity() {
lateinit var navController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestuarantFinderTheme {

                navController = rememberNavController()
                Navigation(navController = navController)
                }
            }
    }
}




