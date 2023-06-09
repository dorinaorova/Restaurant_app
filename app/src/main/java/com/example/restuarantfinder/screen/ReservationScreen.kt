package com.example.restuarantfinder.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.Reservation
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.viewmodel.ReservationViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

private var date = mutableStateOf("")
private var time = mutableStateOf(0)
private var people = mutableStateOf(0)
private var restaurantId : Long = 0
private val vm = ReservationViewModel()
private var enabled = mutableStateOf(date.value.isNotEmpty() && time.value!=0 && people.value!=0)

@Composable
fun ReservationScreen(navController: NavController, id: String?){
    if(id != null) {
        restaurantId = id.toLong()
    }
    LaunchedEffect(Unit, block ={
        vm.getRestaurantDatas(restaurantId)
    } )

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
                text = "Asztal foglalás",
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
    val context = LocalContext.current
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
            )) {
            Spacer(modifier = Modifier.height(20.dp))
            DatePickerForm()
            TimePickerForm()
            PeopleForm()
            Box(modifier = Modifier.padding(horizontal = 60.dp)) {
                Button(
                    onClick = {
                        val df = SimpleDateFormat("yyyy.MM.dd")
                        vm.addReservation(Reservation(0,null,null, df.parse(date.value).time, time.value, people.value),
                            restaurantId,context,navController)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.dark_primary)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = enabled.value

                ) {
                    Text(text = "OK",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
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
    datePickerDialog.datePicker.minDate = mCalendar.timeInMillis

    Box(modifier = Modifier
        .clickable(onClick = { datePickerDialog.show() })
        .fillMaxSize()
        .padding(30.dp)
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
                color = colorResource(R.color.secondary_text))
        }
        date.value = mDate.value
        enabled.value = date.value.isNotEmpty() && time.value!=0 && people.value!=0
    }
}

@Composable
private fun TimePickerForm(){
    var expanded by remember { mutableStateOf(false) }
    val items = createTimeList()
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(size = 16.dp)
        )
        .border(
            width = 2.dp,
            color = colorResource(id = R.color.divider),
            shape = RoundedCornerShape(size = 16.dp)
        )) {
        Row {
            Icon(
                imageVector = Icons.Rounded.Timer,
                contentDescription = null,
                tint = colorResource(id = R.color.secondary_text),
                modifier = Modifier.padding(8.dp)
            )

            Text(items[selectedIndex],
                style = MaterialTheme.typography.subtitle1,
                color = colorResource(R.color.secondary_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(onClick = { expanded = true })
            )

            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                items.forEachIndexed { index, i ->
                    DropdownMenuItem(
                        onClick = {
                            selectedIndex = index
                            expanded = false
                            time.value = getTimeFromForm(items[selectedIndex])
                            enabled.value = date.value.isNotEmpty() && time.value!=0 && people.value!=0
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = i,
                            style = MaterialTheme.typography.subtitle1,
                            color = colorResource(R.color.secondary_text)
                        )
                    }
                }
            }
        }
    }
}

private fun createTimeList(): ArrayList<String>{
    val list = arrayListOf<String>("0")
    if(vm.restaurant.open!=0) {
        list.clear()
        for (i: Int in (vm.restaurant.open / 100) until (vm.restaurant.close / 100) - 1) {
            val hour = if (i < 10) "0$i" else "$i"
            list.add(hour + ":00")
            list.add(hour + ":30")
        }
    }
    return list
}

private fun getTimeFromForm(timeStr: String): Int{
    val time = SimpleDateFormat("HH:mm").parse(timeStr)
    return time.hours*100+time.minutes
}
@Composable
private fun PeopleForm(){
    var expanded by remember { mutableStateOf(false) }
    var items: List<Int>
    if(vm.restaurant.tables>0) {
        items = IntRange(1, vm.restaurant.tables).toList()
    }else{
        items = listOf(0)
    }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(size = 16.dp)
        )
        .border(
            width = 2.dp,
            color = colorResource(id = R.color.divider),
            shape = RoundedCornerShape(size = 16.dp)
        )) {
        Row {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = null,
                tint = colorResource(id = R.color.secondary_text),
                modifier = Modifier.padding(8.dp)
            )

            Text("${items[selectedIndex]}",
                style = MaterialTheme.typography.subtitle1,
                color = colorResource(R.color.secondary_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(onClick = { expanded = true })
            )

            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                items.forEachIndexed { index, i ->
                    DropdownMenuItem(
                        onClick = {
                            selectedIndex = index
                            expanded = false
                            people.value = items[selectedIndex]
                            enabled.value = date.value.isNotEmpty() && time.value!=0 && people.value!=0
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "$i",
                            style = MaterialTheme.typography.subtitle1,
                            color = colorResource(R.color.secondary_text)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground =  true)
fun ReservationScreenPreview(){
    ReservationScreen(navController = rememberNavController(), "0")
}