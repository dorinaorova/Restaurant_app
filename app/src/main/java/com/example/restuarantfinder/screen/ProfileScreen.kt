package com.example.restuarantfinder.screen


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.data.User
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.screen.navbar.NavBar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Composable
fun ProfileScreen(navController: NavController){
    var user = Init()
    var reservations = InitReservations(user)
 Scaffold(
     content = {paddingValues ->
         Box(modifier = Modifier
             .fillMaxSize()
             .padding(paddingValues)){
             Column {
                 UserDetail(user)
                 Reservations(reservations)
             }
         }
     },
     bottomBar ={ NavBar(navController)}
 )
}

@Composable
fun UserDetail(user: User){
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
                    text=user.name,
                    style= TextStyle(
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.primary_text))
                )
                Text(
                    modifier = Modifier.padding(start=8.dp),
                    text=user.email,
                    style = TextStyle(color = colorResource(id = R.color.primary_text))
                )
                Text(
                    modifier = Modifier.padding(start=8.dp),
                    text=user.phone,
                    style = TextStyle(color = colorResource(id = R.color.primary_text))
                )
                Text(
                    modifier = Modifier.padding(start=8.dp),
                    text= convertDate(user.birthDate),
                    style = TextStyle(color = colorResource(id = R.color.primary_text))
                )
            }
            IconButton(
                onClick = { /*TODO*/  })  {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = colorResource(id = R.color.primary_text))
            }
        }
    }
}

@Composable
fun Reservations(reservations: List<Reservation>){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.light_primary))
    ) {
        LazyColumn {
            items(reservations) { item ->
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
                    text = item.restaurant.name,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 24.sp
                )
                Column {
                    Text(text = convertDate(item.time))
                    Text(text = item.people.toString())
                }
                Column{
                    IconButton(
                        onClick = { /*TODO*/  })  {
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

private fun convertDate(time: Long): String{
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd.")
    return format.format(date)
}
private fun Init():User{
    val df = SimpleDateFormat("yyyy.MM.dd")

    var user = User(0,"Példa Név", "email123@email.com","06301234567", df.parse("2000.04.20").time)
    return user
}

private fun InitReservations(user: User): List<Reservation>{
    var reservations = arrayListOf<Reservation>()
    var restaurant = Restaurant(0,"Étterem 1", "Budapest 11.ker", "+36201111111", "email@email.com", "10:00-20:00", null, null)
    val df = SimpleDateFormat("yyyy.MM.dd")
    for (i in 0 .. 5 ){
        reservations.add(Reservation(0,restaurant,user, df.parse("2023.04.20").time,2))
    }
    return reservations
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen( navController = rememberNavController())
    }
}