package com.example.restuarantfinder.screen

import android.util.Log
import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.Reservation
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.viewmodel.MenuViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private var menuTitle =""
private var restaurantId: Long = 0
//private var menu: List<MenuItem> = listOf()
private var vm = MenuViewModel()
@Composable
fun MenuScreen(navController: NavController, title: String?, id: String?){
    if (id != null) {
        restaurantId = id.toLong()
        Log.d("id", "$restaurantId")
    }
    LaunchedEffect(Unit, block ={
        vm.getMenu(menuTitle.contentEquals("Étlap") , restaurantId)
    } )

    menuTitle = title!!

    Box(modifier = Modifier.fillMaxSize()) {
        Column{
            Header()
            MenuList()
        }
    }
}
@Composable
private fun Header(){
    Box(modifier = Modifier.fillMaxWidth()){
        Image(
            painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = menuTitle,
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.h5,
                color = colorResource(id = R.color.primary_text),
                modifier = Modifier.padding(top = 30.dp, bottom = 35.dp)
            )
        }
    }
}

@Composable
private fun MenuList(){
    Column(
        modifier = Modifier
            .background(colorResource(R.color.light_primary))
    ) {
        LazyColumn {
            items(vm.menu) { item ->
                MenuListItem(item)
            }
        }
    }
}

@Composable
private fun MenuListItem(item: MenuItem){
    Box (modifier = Modifier
        .padding(vertical = 12.dp, horizontal = 8.dp)
        .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
        .fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = item.name,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 24.sp
                )
                Text(
                    text = item.price.toString()+" Ft",
                    modifier = Modifier.padding(10.dp),
                )
            }
            if(item.description!=null) {
                Text(
                    text = item.description.toString(),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground =  true)
fun MenuScreenPreview(){
    MenuScreen(navController = rememberNavController(), "Étlap", "0")
}