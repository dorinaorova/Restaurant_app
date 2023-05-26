package com.example.restuarantfinder.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.screen.navbar.NavBar

@Composable
fun RestaurantListScreen(navController: NavController){
    var restaurants = arrayListOf<Restaurant>()
    for (i in 0 .. 25   ) {
        restaurants.add(Restaurant(0,"Étterem 1", "Budapest 11.ker", "+36201111111", "email@email.com", "10:00-20:00", null, null))
        restaurants.add(Restaurant(1, "Étterem 2", "Budapest 12.ker", "+36202222222", "email@email.com", "10:00-20:00",null, null))
    }
    Scaffold(
        content = {
                paddingValues ->
            Box(modifier = Modifier.fillMaxSize()
                .padding(paddingValues)) {
                Column(modifier = Modifier.background(colorResource(R.color.light_primary))) {
                    SearchBar()
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        LazyColumn {
                            items(restaurants) { item ->
                                ListItem(navController, item)
                            }
                        }
                    }
                }
            }
        },
        bottomBar ={ NavBar(navController)}
    )


}
@Composable
fun SearchBar(
){
    var tfvalue by remember {
        mutableStateOf("")
    }
    Box{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentHeight()

        ){
            var value by remember { mutableStateOf("") }
            val context = LocalContext.current

            BasicTextField(
                value = value,
                onValueChange = { value = it },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.secondary_text)
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        Toast.makeText(context, "$value", Toast.LENGTH_SHORT ).show()
                    }
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 10.dp)
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(size = 16.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = colorResource(id = R.color.divider),
                                shape = RoundedCornerShape(size = 16.dp)
                            )
                            .padding(all = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search icon",
                            tint = colorResource(id = R.color.secondary_text)
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun ListItem(navController: NavController ,item: Restaurant = Restaurant(0,"Étterem 1", "Budapest 11.ker", "+36201111111", "email@email.com", "10:00-20:00",null, null)){
    Box (modifier = Modifier
        .padding(vertical = 10.dp, horizontal = 5.dp)
        .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
        .fillMaxWidth()
        .clickable (onClick = {navController.navigate(route = Screen.RestaurantScreen.route)})) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(
                    text = item.name,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 24.sp
                )
                Column {
                    Text(text = item.address)
                    Text(text = item.phone)
                }
            }
        }
    }

}

@Composable
@Preview(showBackground =  true)
fun RestaurantListScreenPreview(){
    RestaurantListScreen(navController = rememberNavController())
}