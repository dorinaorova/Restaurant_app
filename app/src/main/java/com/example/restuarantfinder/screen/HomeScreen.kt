package com.example.restuarantfinder.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.navigation.Screen


@Composable
fun HomeScreen( navController: NavController) {
    Column(
        modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginPage(navController = navController)
    }

}

@Composable
fun LoginPage(navController: NavController) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Column(modifier = Modifier.fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            ImageResource()
        }
        Text(text = "RESTIng", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Serif))

        Spacer(modifier = Modifier.height(20.dp))
        BasicTextField(
            value = email.value,
            onValueChange = { email.value = it},
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
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        BasicTextField(
            value = password.value,
            onValueChange = { password.value = it},
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
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
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
                Text(text = "Login",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable (onClick = {navController.navigate(route = Screen.RestaurantListScreen.route)})
                )
            }
        }

        Box {
            ClickableText(
                text = AnnotatedString("Sign up here"),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = {navController.navigate(route = Screen.SignUpScreen.route) },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = colorResource(id = R.color.primary)
                )
            )
        }
    }
}

@Composable
fun ImageResource(){
    val image: Painter = painterResource(id = R.drawable.train)
    Image(painter = image,contentDescription = "")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen( navController = rememberNavController())
    }
}