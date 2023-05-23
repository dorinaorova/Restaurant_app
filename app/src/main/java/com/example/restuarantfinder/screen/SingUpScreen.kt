package com.example.restuarantfinder.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.navigation.Screen

@Composable
fun SignUpScreen(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        val scroll = rememberScrollState(0)
        Header()
        Datas(scroll, navController)
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
            modifier = Modifier.fillMaxWidth()
                            .height(150.dp),
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.h4,
                color = colorResource(id = R.color.primary_text),
                modifier = Modifier.padding(top = 30.dp, bottom = 60.dp)
            )
        }
    }
}

@Composable
fun Datas(scroll : ScrollState, navController: NavController){
    Column(modifier = Modifier
                    .fillMaxSize()) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
        )
        Column(modifier = Modifier
            .verticalScroll(scroll)
            .fillMaxSize()
            .background(Color.White,
                shape = RoundedCornerShape(size = 30.dp))){
            Spacer(modifier = Modifier.height(20.dp))
            DataField("Full Name", Icons.Rounded.AccountCircle)
            DataField("Email", Icons.Rounded.Email)
            DataField("Phone", Icons.Rounded.Phone)
            DataField("Password", Icons.Rounded.Lock)
            Spacer(modifier = Modifier.height(50.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        navController.navigate(route = Screen.RestaurantListScreen.route)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.dark_primary)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)

                ) {
                    Text(text = "Sign Up",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable (onClick = {navController.navigate(route = Screen.RestaurantListScreen.route)})
                    )
                }
            }
        }

    }
}

@Composable
fun DataField(text: String, icon: ImageVector){
    var tfvalue by remember {
        mutableStateOf("")
    }
    Text(text=text,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp))
    BasicTextField(
        value = "cica",
        onValueChange = { newText ->
            tfvalue = newText
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.secondary_text)
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
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorResource(id = R.color.secondary_text)
                )
                Spacer(modifier = Modifier.width(width = 8.dp))
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MaterialTheme {
        SignUpScreen( navController = rememberNavController())
    }
}