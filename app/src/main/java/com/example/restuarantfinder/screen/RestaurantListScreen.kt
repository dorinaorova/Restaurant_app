package com.example.restuarantfinder.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.FilterVintage
import androidx.compose.material.icons.rounded.LocalBar
import androidx.compose.material.icons.rounded.LocalCafe
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.data.TypeEnum
import com.example.restuarantfinder.navigation.Screen
import com.example.restuarantfinder.screen.navbar.NavBar
import com.example.restuarantfinder.viewmodel.RestaurantListViewModel


private val vm = RestaurantListViewModel()
private var selectedList = mutableStateListOf(false, false, false, false)
@Composable
fun RestaurantListScreen(navController: NavController){
    LaunchedEffect(Unit, block ={
        vm.getRestaurants()
    } )

    Scaffold(
        content = {
                paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.light_primary))) {
                    SearchBar()
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        LazyColumn {
                            items(vm.restaurants) { item ->
                                ListItem(navController, item)
                            }
                        }
                    }
                }
            }
        },
        bottomBar ={ NavBar(navController)}
    )


}
@Composable
fun SearchBar(
){
    Box(modifier = Modifier.background(colorResource(R.color.primary))) {
        Column{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentHeight()

            ) {
                var value by remember { mutableStateOf("") }
                val context = LocalContext.current

                BasicTextField(
                    value = value,
                    onValueChange = { value = it },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.secondary_text)
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (value == "") vm.getRestaurants()
                            else vm.searchByName(value)
                            Toast.makeText(context, "$value", Toast.LENGTH_SHORT).show()
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
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon",
                                tint = colorResource(id = R.color.secondary_text)
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            innerTextField()
                        }
                    }
                )
            }
            Row {
                val typeList = listOf("Vegán", "Állatbarát", "Bár", "Kávézó")

                LazyRow{
                    itemsIndexed(typeList){
                        index, item-> filterBtn(name = item, idx = index )
                        
                    }
                }
            }
        }
    }
}

@Composable
private fun filterBtn(name: String, idx: Int){
    val selected = selectedList.get(idx)
    val color = if (selected) colorResource(R.color.dark_primary) else Color.White

    Button(onClick = {
        setFilter(name)
        selectedList[idx] = !selected
    },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(color),
        modifier = Modifier.padding(8.dp)) {
        Text(
            text=name,
            color= if (selected) colorResource(R.color.primary_text) else colorResource(R.color.secondary_text)
        )
    }
}
private fun setFilter(name: String){
    vm.setFilter(name)
}

@Composable
private fun ListItem(navController: NavController ,item: Restaurant = Restaurant()){
    val route = "${Screen.RestaurantScreen.route}/${item.id}"
    Box (modifier = Modifier
        .padding(vertical = 10.dp, horizontal = 5.dp)
        .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
        .fillMaxWidth()
        .clickable(onClick = { navController.navigate(route = route) })) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.name,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 24.sp
                )
                Column {
                    Text(text = item.address)
                    Text(text = item.phone)
                }
            }
            Row(modifier = Modifier
                .height(10.dp)
                .padding(start = 30.dp)) {
                if (item.types != null) {
                    if (item.types!!.contains(TypeEnum.VEGAN)) {
                        Icon(
                            imageVector = Icons.Rounded.FilterVintage,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }
                    if(item.types!!.contains(TypeEnum.PET)) {
                        Icon(
                            imageVector = Icons.Rounded.Pets,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }
                    if(item.types!!.contains(TypeEnum.CAFE)) {
                        Icon(
                            imageVector = Icons.Rounded.LocalCafe,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }
                    if(item.types!!.contains(TypeEnum.BAR)) {
                        Icon(
                            imageVector = Icons.Rounded.LocalBar,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary_text),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showBackground =  true)
fun RestaurantListScreenPreview(){
    RestaurantListScreen(navController = rememberNavController())
}