package com.example.restuarantfinder.screen

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.api.UserApi
import com.example.restuarantfinder.data.User
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.viewmodel.LoginViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private var email = ""
private var password =""
private val vm = LoginViewModel()

@Composable
fun HomeScreen( navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginPage(navController = navController)
        }
    }
}

@Composable
fun LoginPage(navController: NavController) {
    Box(modifier = Modifier.background(color= Color.White, shape = RoundedCornerShape(size = 16.dp))) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .wrapContentWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val emailForm = remember { mutableStateOf(TextFieldValue()) }
            val passwordForm = remember { mutableStateOf(TextFieldValue()) }

            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageResource()
            }
            Text(
                text = "RESTIng",
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Serif)
            )

            Spacer(modifier = Modifier.height(20.dp))

            val focusManager = LocalFocusManager.current
            BasicTextField(
                value = emailForm.value,
                onValueChange = { emailForm.value = it },
                maxLines = 1,
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
                            .fillMaxWidth(0.8f)
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
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text)
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicTextField(
                value = passwordForm.value,
                onValueChange = { passwordForm.value = it },
                maxLines = 1,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.secondary_text)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 10.dp)
                            .fillMaxWidth(0.8f)
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
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text))
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        password = passwordForm.value.text
                        email = emailForm.value.text
                        var user = User(0,"", email,"", password,0)

                        vm.login(user, navController, context)

                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.dark_primary)),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)

                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box {
                ClickableText(
                    text = AnnotatedString("Sign up here"),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp),
                    onClick = { navController.navigate(route = Screen.SignUpScreen.route) },
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
}

@Composable
fun ImageResource(){
    val image: Painter = painterResource(id = R.drawable.train)
    Image(painter = image,contentDescription = "", modifier = Modifier.padding(top=20.dp))
}

private fun login(user: User, context: Context, navController: NavController){

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navController = rememberNavController())
    }
}