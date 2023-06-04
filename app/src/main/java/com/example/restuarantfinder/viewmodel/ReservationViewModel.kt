package com.example.restuarantfinder.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.restuarantfinder.api.ReservationApi
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.data.Reservation
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.navigation.Screen
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ReservationViewModel : ViewModel(){
    private var _restaurant = mutableStateOf(Restaurant())
    var errorMessage: String by mutableStateOf("")
    val restaurant : Restaurant
        get() = _restaurant.value

    fun getRestaurantDatas(id: Long){
        viewModelScope.launch {
            val api = RestaurantApi.getInstance()
            val call = api.findRestaurant(id)
            val response = call?.awaitResponse()

            if(response?.isSuccessful==true){
                _restaurant.value=response.body()!!
            }
            else{
                Log.d("HIBA ", response.toString())
            }
        }
    }

    fun addReservation(reservation: Reservation, restaurantId: Long, context: Context, navController: NavController) {
        viewModelScope.launch {
            val sharedPref = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            val userId = sharedPref.getLong("USER_ID", 1L)
            try{
                val reservationApi = ReservationApi.getInstance()
                val call = reservationApi.saveReservation(userId, restaurantId,reservation)
                val response = call?.awaitResponse()

                if(response?.isSuccessful == true) {
                    var route = "${Screen.RestaurantScreen.route}/${restaurantId}"
                    navController.navigate(route = route)
                    Toast.makeText(context, "Sikeres foglalás", Toast.LENGTH_SHORT ).show()
                }
                else{
                    Log.d("HIBA ", response.toString())
                }

            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }
}