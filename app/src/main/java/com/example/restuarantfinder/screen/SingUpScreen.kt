package com.example.restuarantfinder.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.navigation.Screen

private var name = ""
private var email = ""
private var phone = ""
private var password = ""

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
                text = "Regisztráció",
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.h4,
                color = colorResource(id = R.color.primary_text),
                modifier = Modifier.padding(top = 30.dp, bottom = 60.dp)
            )
        }
    }
}

@Composable
private fun Datas(scroll : ScrollState, navController: NavController){
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
            DataField("Full Name", Icons.Rounded.AccountCircle, 0)
            DataField("Email", Icons.Rounded.Email, 1)
            DataField("Phone", Icons.Rounded.Phone, 2)
            DataField("Password", Icons.Rounded.Lock, 3)
            Spacer(modifier = Modifier.height(50.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        Toast.makeText(context, "$name $email $phone $password", Toast.LENGTH_SHORT ).show()
                        Log.d("Datas: ", "$name $email $phone $password")
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
                    )
                }
            }
        }

    }
}

@Composable
private fun DataField(text: String, icon: ImageVector, valueNum: Int){
    var value by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Text(text=text,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp))

    BasicTextField(
        value = value,
        onValueChange = {value = it
            if(valueNum==0){
                name = value
                Log.d("name: ", value)
            }
            else if(valueNum ==1){
                email=value
                Log.d("email: ", value)
            }else if(valueNum == 2){
                phone=value
                Log.d("phone: ", value)
            }else{
                password=value
                Log.d("password: ", value)
            }},
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.secondary_text)
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
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
        SignUpScreen(navController = rememberNavController())
    }
}