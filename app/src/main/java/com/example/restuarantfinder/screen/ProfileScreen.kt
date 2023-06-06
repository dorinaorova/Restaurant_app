package com.example.restuarantfinder.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.Reservation
import com.example.restuarantfinder.data.User
import com.example.restuarantfinder.screen.navbar.NavBar
import com.example.restuarantfinder.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*


private val vm = ProfileViewModel()
@Composable
fun ProfileScreen(navController: NavController){
    val context = LocalContext.current
    LaunchedEffect(Unit, block ={
        vm.fetchDatas(context)
    } )
 Scaffold(
     content = {paddingValues ->
         Box(modifier = Modifier
             .fillMaxSize()
             .padding(paddingValues)){
             Column {
                 UserDetail()
                 Reservations()
             }
         }
     },
     bottomBar ={ NavBar(navController)}
 )
}

@Composable
fun UserDetail(){
    Row(modifier = Modifier
        .background(colorResource(id = R.color.primary))
        .fillMaxWidth()
        .padding(16.dp)){
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.train),
            contentDescription = "Profile picture"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Column(modifier = Modifier.padding(start = 16.dp, top=5.dp)){
                Text(
                    text=vm.user.name,
                    style= TextStyle(
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.primary_text)),
                )
                Text(
                    modifier = Modifier.padding(start=8.dp, top=5.dp),
                    text=vm.user.email,
                    style = TextStyle(color = colorResource(id = R.color.primary_text)),
                )
                Text(
                    modifier = Modifier.padding(start=8.dp, top=5.dp),
                    text=vm.user.phone,
                    style = TextStyle(color = colorResource(id = R.color.primary_text))
                )
                Text(
                    modifier = Modifier.padding(start=8.dp, top=5.dp),
                    text= convertDate(vm.user.birthDate),
                    style = TextStyle(color = colorResource(id = R.color.primary_text))
                )
            }
        }
    }
}

@Composable
fun Reservations(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.light_primary))
    ) {
        LazyColumn {
            items(vm.reservations) { item ->
                ListItem(item)
            }
        }
    }
}

@Composable
private fun ListItem(item: Reservation){
    Box (modifier = Modifier
        .padding(vertical = 12.dp, horizontal = 8.dp)
        .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
        .fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.restaurant!!.name,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 24.sp
                )
                Column {
                    Text(text = "${convertDate(item.date)} ${createTimeStr(item.time)}")
                    Text(text = "${item.people} fő")
                }
                Column{
                    IconButton(
                        onClick = {
                            vm.deleteReservation(item.id)
                        })  {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text))
                    }
                }
            }
        }
    }
}

private fun createTimeStr(time: Int): String{
    val hours = if(time/100 <10) "0${time/100}" else "${time/100}"
    val minutes = if(time%100 <10) "0${time%100}" else "${time%100}"
    return "$hours:$minutes"
}

private fun convertDate(time: Long): String{
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd.")
    return format.format(date)
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(navController = rememberNavController())
    }
}