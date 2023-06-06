package com.example.restuarantfinder.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.TypeEnum
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.screen.navbar.NavBar
import com.example.restuarantfinder.viewmodel.RestaurantViewModel

private var vm = RestaurantViewModel()
private var restaurantId : Long =0

@Composable
fun RestaurantScreen(navController: NavController, id: String?){
    if (id != null) {
        restaurantId = id.toLong()
    }

    LaunchedEffect(Unit, block ={
        vm.getRestaurantDatas(restaurantId)
    } )

    Scaffold(
        content = {paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)){
            RestaurantDetails(navController)
            }
        },
        bottomBar ={ NavBar(navController)}
    )
}

@Composable
fun RestaurantDetails(navController: NavController){
        Box(Modifier.fillMaxSize()){
            val scroll = rememberScrollState(0)
            Header()
            Body(scroll, navController)
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
fun Body(scroll: ScrollState, navController: NavController){

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
                    text=vm.restaurant.name,
                    style= MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = CreateOpenString(),
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )
                    Row(modifier = Modifier
                        .height(10.dp)
                        .padding(top = 8.dp)) {
                        if (vm.restaurant.types != null) {
                            if (vm.restaurant.types!!.contains(TypeEnum.VEGAN)) {
                                Icon(
                                    imageVector = Icons.Rounded.FilterVintage,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.secondary_text),
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                            }
                            if(vm.restaurant.types!!.contains(TypeEnum.PET)) {
                                Icon(
                                    imageVector = Icons.Rounded.Pets,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.secondary_text),
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                            }
                            if(vm.restaurant.types!!.contains(TypeEnum.CAFE)) {
                                Icon(
                                    imageVector = Icons.Rounded.LocalCafe,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.secondary_text),
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                            }
                            if(vm.restaurant.types!!.contains(TypeEnum.BAR)) {
                                Icon(
                                    imageVector = Icons.Rounded.LocalBar,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.secondary_text),
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
            Text(
                text=vm.restaurant.address,
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
                            contentDescription = null
                        )
                        Text(
                            text = vm.restaurant.phone,
                            modifier = Modifier.padding(start=10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(start = 30.dp,top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Email,
                            contentDescription = null
                        )
                        Text(
                            text = vm.restaurant.email,
                            modifier = Modifier.padding(start=10.dp)
                        )
                    }
                }
                IconButton(onClick = {
                    val route = Screen.ReservationScreen.route+"/"+vm.restaurant.id
                    navController.navigate(route = route) },
                            modifier = Modifier.padding(end = 30.dp)){
                            Icon(
                                Icons.Rounded.List,
                                contentDescription = null,
                                tint = colorResource(id = R.color.secondary_text)
                            )
                        }
            }
            if(vm.restaurant.menu_Food != null) {
                MenuRow(vm.restaurant.menu_Food!!, "Étlap", navController)
            }
            if(vm.restaurant.menu_Drink!=null) {
                MenuRow(vm.restaurant.menu_Drink!!, "Itallap", navController)
            }
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
        val route = Screen.MenuScreen.route+"/"+title+"/"+ restaurantId
        IconButton(onClick = {navController.navigate(route = route)},
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
        items(CreateList(list)){
                item -> MenuListItem(item = item)
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
        val menu = arrayListOf<MenuItem>()
        for (i in 0..5) {
            menu.add(menuBase[i])
        }
        return menu
    }else return menuBase
}

private fun CreateOpenString():String{
    val openMinutes = if(vm.restaurant.open%100 == 0) "00" else (vm.restaurant.open%100).toString()
    val openHours = if(vm.restaurant.open/100 < 10) "0${vm.restaurant.open/100}" else (vm.restaurant.open/100).toString()
    val closeMinutes = if(vm.restaurant.close%100 == 0) "00" else (vm.restaurant.close%100).toString()
    val closeHours = if(vm.restaurant.close/100 < 10) "0${vm.restaurant.close/100}" else (vm.restaurant.close/100).toString()
    val openStr= "$openHours:$openMinutes - $closeHours:$closeMinutes"
    return openStr
}
@Composable
@Preview(showBackground =  true)
fun RestaurantScreenPreview(){
    RestaurantScreen(navController = rememberNavController(), "0")
}