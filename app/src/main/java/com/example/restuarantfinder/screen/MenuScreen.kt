package com.example.restuarantfinder.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.navigation.Screen

@Composable
fun MenuScreen(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Header()
    }
}

@Composable
private fun Header(){
    Box(modifier = Modifier.fillMaxSize()){
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
            )
        }
    }
}


@Composable
@Preview(showBackground =  true)
fun MenuScreenPreview(){
    MenuScreen(navController = rememberNavController())
}