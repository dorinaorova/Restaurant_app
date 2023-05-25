package com.example.restuarantfinder.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.Reservation
import com.example.restuarantfinder.navigation.Screen

@Composable
fun MenuScreen(navController: NavController){
    var menu = Init()
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Header(navController)
            MenuList(menu)
        }
    }
}
@Composable
private fun Header(navController: NavController){
    Box(modifier = Modifier.fillMaxWidth()){
        Image(
            painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
        )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Étlap",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun MenuList(menu: List<MenuItem>){
    Column(
        modifier = Modifier
            //.padding(8.dp)
            .background(colorResource(R.color.light_primary))
    ) {
        LazyColumn {
            items(menu) { item ->
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
            Text(
                text=item.description,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

private fun Init(): List<MenuItem>{
    var menu = arrayListOf<MenuItem>()
    for(i in 0.. 10){
        var item = MenuItem("Food$i", (i+1)*1000, "Cat ipsum dolor sit amet, attack like a vicious monster and where is it? i saw that bird i need to bring it home to mommy squirrel! need to check on human,")
        menu.add(item)
    }
    return menu
}

@Composable
@Preview(showBackground =  true)
fun MenuScreenPreview(){
    MenuScreen(navController = rememberNavController())
}