package com.example.restuarantfinder.screen

import android.util.Log
import android.view.Menu
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.screen.navbar.NavBar

private val TitleHeight = 128.dp

@Composable
fun RestaurantScreen(navController: NavController){
    var restaurant= Init()
    Scaffold(
        content = {paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)){
            RestaurantDetails(restaurant, navController)
            }
        },
        bottomBar ={ NavBar(navController)}
    )
}

@Composable
fun RestaurantDetails(restaurant: Restaurant, navController: NavController){
        Box(Modifier.fillMaxSize()){
            val scroll = rememberScrollState(0)
            Header()
            Body(restaurant, scroll, navController)
        }
}

@Composable
private fun Header(){
    Box(modifier = Modifier
        .fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
        )
    }
}

@Composable
fun Body(restaurant: Restaurant, scroll: ScrollState, navController: NavController){

    Column{
        Spacer(modifier = Modifier
            .height(260.dp)
            .fillMaxWidth())
        Column(modifier = Modifier
            .verticalScroll(scroll)
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.white),
                shape = RoundedCornerShape(size = 30.dp)
            )){
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    text=restaurant.name,
                    style= MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Text(
                    text=restaurant.open,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }
            Text(
                text=restaurant.address,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(start = 40.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Row(
                        modifier = Modifier.padding(start = 30.dp, top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Phone,
                            contentDescription = "Restaurant phone"
                        )
                        Text(
                            text = restaurant.phone,
                            modifier = Modifier.padding(start=5.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(start = 30.dp,top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Email,
                            contentDescription = "Restaurant email"
                        )
                        Text(
                            text = restaurant.email,
                        )
                    }
                }
                IconButton(onClick = { navController.navigate(route = Screen.ReservationScreen.route) },
                            modifier = Modifier.padding(end = 30.dp)){
                            Icon(
                                Icons.Rounded.List,
                                contentDescription = null,
                                tint = colorResource(id = R.color.secondary_text)
                            )
                        }
            }
            MenuRow(restaurant.menu_Food!!, "Étlap", navController)
            MenuRow(restaurant.menu_Drink!!, "Itallap", navController)
        }
    }
}

@Composable
fun MenuRow(list: List<MenuItem>, title: String, navController: NavController){
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(top = 20.dp)) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
        )
    }
    Row(modifier = Modifier
        .padding(top = 35.dp, bottom = 10.dp, start = 20.dp, end = 40.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ){
        Text(text=title,
            style= MaterialTheme.typography.h6,
            color = colorResource(id = R.color.secondary_text))
        Log.d("Route", Screen.MenuScreen.route+"/"+title)
        IconButton(onClick = {navController.navigate(route = Screen.MenuScreen.route)},
        modifier = Modifier
            .size(10.dp)
            .background(
                colorResource(id = R.color.light_primary), shape = CircleShape
            )) {
            Icon(
                Icons.Rounded.ArrowForward,
                contentDescription = null
            )
        }
    }
    LazyRow{
        itemsIndexed(CreateList(list)){
                index, item -> MenuListItem(item = item)
        }
    }
}
@Composable
private fun MenuListItem(item: MenuItem){
    Box(modifier = Modifier
        .height(150.dp)
        .width(200.dp)
        .padding(10.dp)
        .background(
            colorResource(id = R.color.light_primary),
            shape = RoundedCornerShape(size = 16.dp)
        )){
        Text(text = item.name,
            color = colorResource(id = R.color.secondary_text),
            style=MaterialTheme.typography.subtitle1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun CreateList(menuBase: List<MenuItem>):List<MenuItem>{
    if(menuBase.size>5) {
        var menu = arrayListOf<MenuItem>()
        for (i in 0..5) {
            menu.add(menuBase[i])
        }
        return menu
    }else return menuBase
}

private fun Init(): Restaurant {
    var restaurant = Restaurant(0,"Étterem 1", "Budapest 11.ker", "+36201111111", "email@email.com", "10:00-20:00", null, null)

    val food1 = MenuItem("Food1", 2000, "Cat ipsum dolor sit amet, russian blue, thai and sphynx so savannah. Malkin cornish rex but tom malkin for cheetah scottish fold.")
    val food2 = MenuItem("Food2", 2300, "Cat ipsum dolor sit amet, russian blue, thai and sphynx so savannah. Malkin cornish rex but tom malkin for cheetah scottish fold.")
    val food3 = MenuItem("Food3", 2450, "Cat ipsum dolor sit amet, russian blue, thai and sphynx so savannah. Malkin cornish rex but tom malkin for cheetah scottish fold.")
    val food4 = MenuItem("Food4", 3000, "Cat ipsum dolor sit amet, russian blue, thai and sphynx so savannah. Malkin cornish rex but tom malkin for cheetah scottish fold.")

    restaurant.menu_Food = listOf(food1, food2, food3, food4)

    val drink1 = MenuItem("Drink1", 1200, "Cat ipsum dolor sit amet, chase laser. Make plans to dominate world and then take a nap take a deep sniff of sock then walk around with mouth half open for my left donut is missing, as is my right and this cat happen now, it was too purr-fect!!!")
    val drink2 = MenuItem("Drink2", 1200, "Cat ipsum dolor sit amet, chase laser. Make plans to dominate world and then take a nap take a deep sniff of sock then walk around with mouth half open for my left donut is missing, as is my right and this cat happen now, it was too purr-fect!!!")
    val drink3 = MenuItem("Drink3", 1200, "Cat ipsum dolor sit amet, chase laser. Make plans to dominate world and then take a nap take a deep sniff of sock then walk around with mouth half open for my left donut is missing, as is my right and this cat happen now, it was too purr-fect!!!")
    val drink4 = MenuItem("Drink4", 1200, "Cat ipsum dolor sit amet, chase laser. Make plans to dominate world and then take a nap take a deep sniff of sock then walk around with mouth half open for my left donut is missing, as is my right and this cat happen now, it was too purr-fect!!!")
    val drink5 = MenuItem("Drink5", 1200, "Cat ipsum dolor sit amet, chase laser. Make plans to dominate world and then take a nap take a deep sniff of sock then walk around with mouth half open for my left donut is missing, as is my right and this cat happen now, it was too purr-fect!!!")

    restaurant.menu_Drink = listOf(drink1, drink2, drink3, drink4,drink5 )

    return restaurant
}

@Composable
@Preview(showBackground =  true)
fun RestaurantScreenPreview(){
    RestaurantScreen(navController = rememberNavController())
}