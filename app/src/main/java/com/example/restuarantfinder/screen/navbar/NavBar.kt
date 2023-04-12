package com.example.restuarantfinder.screen.navbar

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R


val bottomNavItems = listOf(
    BottomNavItem(
        name = "Home",
        route = "home_screen",
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        name = "Search",
        route = "list_screen",
        icon = Icons.Rounded.Search,
    ),
    BottomNavItem(
        name = "Profile",
        route = "profile_screen",
        icon = Icons.Rounded.Person,
    ),
)

@Composable
fun NavBar(navController: NavController){

    val backStackEntry = navController.currentBackStackEntryAsState()


    NavigationBar(containerColor = colorResource(id = R.color.primary))
    {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                        tint = colorResource(id = R.color.primary_text)
                    )
                }
            )
        }
    }

}

@Composable
@Preview(showBackground =  true)
fun NavBarPreview(){
    NavBar(navController = rememberNavController())
}