package com.example.restuarantfinder.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.data.Restaurant
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class RestaurantListViewModel: ViewModel() {
    private var _restaurants = mutableStateListOf<Restaurant>()
    var errorMessage: String by mutableStateOf("")
    val restaurants : List<Restaurant>
        get() = _restaurants

    fun getRestaurants(){
        viewModelScope.launch {
            try{
                val api = RestaurantApi.getInstance()
                val call = api.getAllRestaurants()
                val response = call?.awaitResponse()

                if(response?.isSuccessful == true){
                    _restaurants.clear()
                    _restaurants.addAll(response.body()!!)
                }
            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }
}