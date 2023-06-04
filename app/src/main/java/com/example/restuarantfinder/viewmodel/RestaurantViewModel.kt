package com.example.restuarantfinder.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.Restaurant
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class RestaurantViewModel : ViewModel(){
    private var _restaurant : Restaurant by mutableStateOf(Restaurant())
    var errorMessage: String by mutableStateOf("")
    val restaurant : Restaurant
        get() = _restaurant

    fun getRestaurantDatas(id: Long){
        viewModelScope.launch {
            try{
                val api = RestaurantApi.getInstance()
                val call_Restaurant = api.findRestaurant(id)
                val response_Restaurant = call_Restaurant?.awaitResponse()

                if(response_Restaurant?.isSuccessful == true){
                    _restaurant = response_Restaurant.body()!!
                }

            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }
}