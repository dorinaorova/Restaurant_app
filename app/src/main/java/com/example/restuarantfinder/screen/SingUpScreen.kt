package com.example.restuarantfinder.screen

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.api.UserApi
import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.User
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.viewmodel.SignUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

private var name  = mutableStateOf("")
private var email = mutableStateOf("")
private var phone = mutableStateOf("")
private var password = mutableStateOf("")
private var date = mutableStateOf("")
private var enabled = mutableStateOf(name.value.isNotEmpty() && email.value.isNotEmpty() && phone.value.isNotEmpty() && password.value.isNotEmpty())
private var vm = SignUpViewModel()

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
            modifier = Modifier
                .fillMaxWidth()
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
            .background(
                Color.White,
                shape = RoundedCornerShape(size = 30.dp)
            )){
            Spacer(modifier = Modifier.height(20.dp))
            DataField("Teljes név", Icons.Rounded.AccountCircle, 0, ImeAction.Next,  VisualTransformation.None)
            DataField("Email", Icons.Rounded.Email, 1, ImeAction.Next, VisualTransformation.None)
            DataField("Telefonszám", Icons.Rounded.Phone, 2,ImeAction.Done, VisualTransformation.None)
            DatePickerForm()
            DataField("Jelszó", Icons.Rounded.Lock, 3, ImeAction.Done, PasswordVisualTransformation())
            Spacer(modifier = Modifier.height(50.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                val context = LocalContext.current

                Button(
                    onClick = {
                        val df = SimpleDateFormat("yyyy.MM.dd")
                        val user= User(0,name.value, email.value, phone.value, password.value,df.parse(date.value).time)
                        vm.signUp(user,navController,context)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.dark_primary)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(bottom = 10.dp),
                    enabled = enabled.value

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
private fun DatePickerForm(){
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(mContext,R.style.DialogTheme,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mYear. ${mMonth+1}. $mDayOfMonth."
        }, mYear, mMonth, mDay
    )
    mCalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue-1, LocalDateTime.now().dayOfMonth)
    datePickerDialog.datePicker.maxDate = mCalendar.timeInMillis



    Text(text="Születési dátum",
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(start = 20.dp, top = 10.dp))
    Box(modifier = Modifier
        .clickable(onClick = { datePickerDialog.show() })
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 10.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(size = 16.dp)
        )
        .border(
            width = 2.dp,
            color = colorResource(id = R.color.divider),
            shape = RoundedCornerShape(size = 16.dp)
        )) {
        Row{
            Icon(
                imageVector = Icons.Rounded.DateRange,
                contentDescription = null,
                tint = colorResource(id = R.color.secondary_text),
                modifier = Modifier.padding(8.dp)
            )

            Text(text = mDate.value,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.subtitle1,
                color = colorResource(R.color.secondary_text),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
                )
        }

        date.value = mDate.value;
    }
}

@Composable
private fun DataField(text: String, icon: ImageVector, valueNum: Int, imeAction: ImeAction, visualTransformation: VisualTransformation){
    var value by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Text(text=text,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(start = 20.dp, top = 10.dp))

    BasicTextField(
        value = value,
        onValueChange = {value = it
            enabled.value = name.value.isNotEmpty() && email.value.isNotEmpty() && phone.value.isNotEmpty() && password.value.isNotEmpty() && date.value.isNotEmpty()
            if(valueNum==0){
                name.value = value
            }
            else if(valueNum ==1){
                email.value=value
            }else if(valueNum == 2){
                phone.value=value
            }else{
                password.value=value
            }},
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.secondary_text)
        ),
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        visualTransformation = visualTransformation,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                focusManager.clearFocus()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.divider),
                        shape = RoundedCornerShape(size = 16.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorResource(id = R.color.secondary_text),
                    modifier = Modifier.padding(8.dp)
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