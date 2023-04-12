package com.example.restuarantfinder.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.screen.navbar.NavBar

@Composable
fun ProfileScreen(navController: NavController){
 Scaffold(
     content = {
         Modifier.padding(it)
         UserDetail()
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
            Column(modifier = Modifier.padding(start = 16.dp)){
                Text(
                    text="Név",
                    style= TextStyle(
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.primary_text))
                )
                Text(
                    modifier = Modifier.padding(start=8.dp),
                    text="email123@email.com",
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen( navController = rememberNavController())
    }
}